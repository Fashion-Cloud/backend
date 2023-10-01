package com.techeer.fashioncloud.domain.geo.controller;

import com.techeer.fashioncloud.domain.geo.dto.AddressResponse;
import com.techeer.fashioncloud.domain.geo.service.AddressService;
import com.techeer.fashioncloud.global.response.ResponseCode;
import com.techeer.fashioncloud.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "위치 반환", description ="위경도를 이용해 위치를 반환한다.")
    public ResponseEntity<ResultResponse> getAddressHere(
            @Parameter(name="latitude") @RequestParam Double latitude,
            @Parameter(name="longitude") @RequestParam Double longitude
    ) {
        AddressResponse address = addressService.getAddress(latitude, longitude);
        return ResponseEntity.ok(ResultResponse.of(ResponseCode.ADDRESS_GET_SUCCESS, address));
    }
}