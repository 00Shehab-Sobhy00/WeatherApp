package com.example.backend.service;

import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;

public interface UserAndAdminDetailsService {

    UserInfoDTO viewUserDetails (Integer userId);

      EditeUserDetailsDTO updateUserDetails (EditeUserDetailsDTO userDetails , Integer userId);


    UserInfoDTO viewAdminDetails (Integer userId);

    EditeUserDetailsDTO updateAdminDetails (EditeUserDetailsDTO userDetails , Integer userId);
}
