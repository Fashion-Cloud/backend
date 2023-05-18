package com.techeer.fashioncloud.global.domain;

import com.fasterxml.jackson.databind.JsonNode;

public interface ExternalApiCallable<T> {

    T parseApiResponse(JsonNode jsonNode);
}

