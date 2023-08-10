package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.domain.weather.service.AddressService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    private final AddressService addressService;

    @GetMapping()
    @Operation(summary = "위치 반환", description ="위경도를 이용해 위치를 반환한다.")
    public ResponseEntity<ResultResponse> getAddressHere(
            @Parameter(name="latitude") @RequestParam Double latitude,
            @Parameter(name="longitude") @RequestParam Double longitude
            ) throws ParseException {
        AddressResponse address = addressService.getAddress(new Location(latitude, longitude));
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.ADDRESS_GET_SUCCESS, address));
    }
}