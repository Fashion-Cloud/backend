package com.techeer.fashioncloud.domain.User.transformer;


import com.techeer.fashioncloud.domain.User.dto.requestDto.SignupDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.UserDTO;
import com.techeer.fashioncloud.domain.User.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTransformer {
    public static User signUpDtoToUser(SignupDTO signupDTO){
        return User.builder()
                .nickname(signupDTO.getNickname())
                .profileUrl(signupDTO.getProfileUrl())
                .address(signupDTO.getAddress())
                .email(signupDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(signupDTO.getPassword()))
                .build();
    }

    public static UserDTO userToUserDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .address(user.getAddress())
                .email(user.getEmail())
                .build();
    }
}
