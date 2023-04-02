package com.techeer.fashioncloud.domain.weather.controller;

import com.techeer.fashioncloud.domain.weather.dto.AddressResponse;
import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.domain.weather.service.AddressService;
import com.techeer.fashioncloud.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.techeer.fashioncloud.global.dto.ApiResponse.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping()
    public ApiResponse getWeatherHere(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) throws ParseException, org.json.simple.parser.ParseException {

        AddressResponse address = addressService.getAddress(new Location(latitude, longitude));
        return ok(address);

    }
}
