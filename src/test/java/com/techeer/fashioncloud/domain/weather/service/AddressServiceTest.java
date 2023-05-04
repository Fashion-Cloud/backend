package com.techeer.fashioncloud.domain.weather.service;

import com.techeer.fashioncloud.domain.weather.position.Location;
import com.techeer.fashioncloud.global.config.GeoConfig;
import com.techeer.fashioncloud.global.error.exception.ApiBadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class AddressServiceTest {

    @Mock
    private GeoConfig geoConfig;

    private AddressService addressService;
    @BeforeEach
    public void setUp() {
        addressService = new AddressService(geoConfig);
    }

    @Test
    @DisplayName("인증키 없이 api 호출 시도시 ApiBadRequestException 발생한다")
    public void invalidLocation() {
        // given
        Location location = new Location(35.0,127.0);

        //when, then
        Assertions.assertThrows(ApiBadRequestException.class, () -> addressService.getAddress(location));

    }



}