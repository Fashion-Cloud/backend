package com.techeer.fashioncloud.domain.geo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.geo.dto.GeoApiRequest;
import com.techeer.fashioncloud.global.util.api.ExternalApiCallable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

@Component
public class GeoApiCaller implements ExternalApiCallable<GeoApiRequest> {

    @Value("${openapi.geocoding.key}")
    private String geoKey;

    private String headerName = "Authorization";
    
    private String headerValue;

    @PostConstruct
    public void setHeaderValue() {
        this.headerValue = "KakaoAK " + geoKey;
    }

    @Override
    public Mono<JsonNode> get(GeoApiRequest geoApiRequest) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(geoApiRequest.getPath());

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", geoApiRequest.getLongitude())
                        .queryParam("y", geoApiRequest.getLatitude())
                        .build())
                .header(headerName, headerValue)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}