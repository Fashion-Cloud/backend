package com.techeer.fashioncloud.domain.geo.controller;

import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;
import com.techeer.fashioncloud.domain.geo.service.AddressService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping()
    public ResponseEntity<ResultResponse> getAddressHere(
            @RequestParam Double latitude,
            @RequestParam Double longitude
            ) {
        AddressResponse address = addressService.getAddress(latitude, longitude);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.ADDRESS_GET_SUCCESS, address));
    }
}