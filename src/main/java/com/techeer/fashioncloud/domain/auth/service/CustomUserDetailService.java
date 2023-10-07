package com.techeer.fashioncloud.domain.auth.service;

import com.techeer.fashioncloud.domain.auth.util.CustomUser;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByEmail(email)
                .map(CustomUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("해당 email을 가진 유저가 존재하지 않습니다 - email: " + email));
        return customUser;
    }
}