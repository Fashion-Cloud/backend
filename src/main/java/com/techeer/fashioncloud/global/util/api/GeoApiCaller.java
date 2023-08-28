package com.techeer.fashioncloud.global.util.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.global.util.api.dto.GeoApiRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

public class GeoApiCaller implements ExternalApiCallable<GeoApiRequest> {

    @Override
    public Mono<JsonNode> get(GeoApiRequest geoApiRequest) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(geoApiRequest.getPath());

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.queryParams(geoApiRequest.getQueryParams()).build())
                .header(geoApiRequest.getHeaderName(), geoApiRequest.getHeaderValues())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}