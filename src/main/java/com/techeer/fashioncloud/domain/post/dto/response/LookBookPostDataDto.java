package com.techeer.fashioncloud.domain.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LookBookPostDataDto {
    private UUID id;
    private UUID postId;
    private UUID lookBookId;
    // Include other necessary fields
}
