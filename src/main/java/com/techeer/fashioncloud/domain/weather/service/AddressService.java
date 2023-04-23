package com.techeer.fashioncloud.domain.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.constant.GeoConstant;
import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.GeoConfig;
import com.techeer.fashioncloud.global.exception.ApiParseException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class AddressService {


    private final GeoConfig geoConfig;

    public AddressResponse getAddress (Location location) throws ParseException {

        String latitude = location.getLatitude().toString();
        String longitude = location.getLongitude().toString();

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(GeoConstant.GEO_BASE_URL);


        WebClient webclient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        Mono<JsonNode> responseMono = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", longitude)
                        .queryParam("y",latitude)
                        .build())
                .header("Authorization", "KakaoAK "+geoConfig.getKey())
                .exchangeToMono(response -> {
                    Integer httpStatusCode = response.statusCode().value();
                    HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode);
                    if (httpStatus.is2xxSuccessful()) {
                        return response.bodyToMono(JsonNode.class);
                    } else {
                        // HTTP 에러 처리
                        throw new RuntimeException("HTTP Error: " + httpStatus);
                    }
                });
        return responseMono.map(jsonNode -> {
            try {
                return parseAddress(jsonNode);
            } catch (Exception e) {
                throw new ApiParseException();
            }
        }).block();

    }

    public AddressResponse parseAddress(JsonNode jsonNode) throws ParseException {

        JsonNode documentsNode = jsonNode.get("docments");
        JsonNode regionNode = documentsNode.get(0);
        String fullAddress = regionNode.get("address_name").asText();
        String region1depth = regionNode.get("region_1depth_name").asText();
        String region2depth = regionNode.get("region_2depth_name").asText();
        String region3depth = regionNode.get("region_3depth_name").asText();

        return AddressResponse.builder()
                .fullAddress(fullAddress)
                .region1depth(region1depth)
                .region2depth(region2depth)
                .region3depth(region3depth)
                .build();
    }
}
