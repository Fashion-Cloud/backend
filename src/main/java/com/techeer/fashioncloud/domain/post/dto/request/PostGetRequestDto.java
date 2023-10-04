package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostGetRequestDto {

    @Parameter(description = "기상청 하늘상태")
    @ValidEnum(enumClass = SkyStatus.class)
    private SkyStatus skyStatus;


    @Parameter(description = "강수형태")
    @ValidEnum(enumClass = RainfallType.class)
    private RainfallType rainFallType;

    @Parameter(description = "체감온도")
    private Double windChill;
}
