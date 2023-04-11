package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.GeoConstant;
import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.GeoConfig;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;


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

        String response = webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", longitude)
                        .queryParam("y",latitude)
                        .build())

                .header("Authorization", "KakaoAK "+geoConfig.getKey())
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .block();

        return parseAddress(response);
    }

    public AddressResponse parseAddress(String apiResponse) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(apiResponse);
        JSONArray documents = (JSONArray) jsonObject.get("documents");
        JSONObject document = (JSONObject) documents.get(0);

        String fullAddress = (String) document.get("address_name");
        String region1depth = (String) document.get("region_1depth_name");
        String region2depth = (String) document.get("region_2depth_name");
        String region3depth = (String) document.get("region_3depth_name");

        return AddressResponse.builder()
                .fullAddress(fullAddress)
                .region1depth(region1depth)
                .region2depth(region2depth)
                .region3depth(region3depth)
                .build();
    }

}
