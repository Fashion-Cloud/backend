package com.techeer.fashioncloud.domain.weather.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.domain.weather.dto.external.WeatherApiRequest;
import com.techeer.fashioncloud.global.util.api.ExternalApiCallable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Component
public class WeatherApiCaller implements ExternalApiCallable<WeatherApiRequest> {

    @Value("${openapi.weather.key.encoding}")
    private String weatherEncodingKey;

    @Value("${openapi.weather.key.decoding}")
    private String weatherDecodingKey;

    private HashMap<String, Object> baseQueryParams = new HashMap<>();

    @PostConstruct
    public void setBaseQueryParams() {
        this.baseQueryParams.put("serviceKey", weatherDecodingKey);
        this.baseQueryParams.put("dataType", "JSON");
    }

    @Override
    public Mono<JsonNode> get(WeatherApiRequest weatherApiRequest) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(weatherApiRequest.getPath());

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .build()
                .get()
                .uri(uriBuilder -> {
                    this.baseQueryParams.forEach(uriBuilder::queryParam);
                    weatherApiRequest.getQueryParams().forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}