package com.techeer.fashioncloud.domain.post.dto.request;

import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostGetRequestDto {

    @Parameter(description = "기상청 하늘상태")
    @ValidEnum(enumClass = SkyStatus.class)
    private SkyStatus skyStatus;

    @Parameter(description = "강수형태")
    @ValidEnum(enumClass = RainfallType.class)
    private RainfallType rainfallType;

    @Parameter(description = "최저 체감온도")
    private Double minWindChill;

    @Parameter(description = "최고 체감온도")
    private Double maxWindChill;
}