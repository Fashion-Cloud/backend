package com.techeer.fashioncloud.domain.User.service.impl;


import com.techeer.fashioncloud.domain.User.dto.requestDto.SignupDTO;
import com.techeer.fashioncloud.domain.User.dto.responseDto.UserDTO;
import com.techeer.fashioncloud.domain.User.exception.UserAlreadyPresentException;
import com.techeer.fashioncloud.domain.User.model.User;
import com.techeer.fashioncloud.domain.User.repository.UserRepository;
import com.techeer.fashioncloud.domain.User.service.AuthService;
import com.techeer.fashioncloud.domain.User.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDTO createUser(SignupDTO signupDTO) throws UserAlreadyPresentException {
        //check if user already present
        Optional<User> userOpt = userRepository.findByEmail(signupDTO.getEmail());
        if(userOpt.isPresent()){
            throw new UserAlreadyPresentException("User already present with this Email Id");
        }
        User user = UserTransformer.signUpDtoToUser(signupDTO);
        User createdUser = userRepository.save(user);
        UserDTO userDto = UserTransformer.userToUserDto(createdUser);
        return userDto;
//        User user = new User();
//        user.setName(signupDTO.getName());
//        user.setEmail(signupDTO.getEmail());
//        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
//        User createdUser = userRepository.save(user);
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(createdUser.getId());
//        userDTO.setEmail(createdUser.getEmail());
//        userDTO.setName(createdUser.getName());
//        return userDTO;
    }

}
