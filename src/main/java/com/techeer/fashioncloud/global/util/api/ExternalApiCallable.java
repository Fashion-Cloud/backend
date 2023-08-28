package com.techeer.fashioncloud.global.util.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.techeer.fashioncloud.global.util.api.dto.ExternalApiRequest;
import reactor.core.publisher.Mono;

public interface ExternalApiCallable<T extends ExternalApiRequest> {

    Mono<JsonNode> get(T externalApiRequest);

}