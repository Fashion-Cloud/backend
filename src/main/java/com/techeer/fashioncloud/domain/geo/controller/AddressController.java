package com.techeer.fashioncloud.domain.geo.controller;

import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.domain.geo.service.AddressService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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