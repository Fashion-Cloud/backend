package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.constant.GeoConstant;
import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.GeoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;


@Service
@RequiredArgsConstructor
public class AddressService {


    private final GeoConfig geoConfig;

    public AddressResponse getAddress (Location location) {

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

        return AddressResponse.builder()
                .address(response)
                .build();
    }

}
