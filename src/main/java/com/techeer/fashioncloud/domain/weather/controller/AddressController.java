package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.domain.weather.service.AddressService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
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
            ) throws ParseException {
        AddressResponse address = addressService.getAddress(new Location(latitude, longitude));
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.ADDRESS_GET_SUCCESS, address));
    }
}