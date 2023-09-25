package com.example.backend.controller;


import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.service.UserAndAdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserDetailsController {

    private final UserAndAdminDetailsService userAndAdminDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserInfoDTO> getUserDetails(@PathVariable Integer userId) {
        logger.info("getUserDetails()");
        logger.debug("userId : {} " , userId);
        return ResponseEntity.ok(userAndAdminDetailsService.viewUserDetails(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<EditeUserDetailsDTO> updateUserDetails(
            @PathVariable Integer userId, @RequestBody EditeUserDetailsDTO userDetails) {
        logger.info("updateUserDetails()");
        logger.debug("userId : {} , EditeUserDetailsDTO : {}" , userId , userDetails);
        return ResponseEntity.ok(userAndAdminDetailsService.updateUserDetails( userDetails,userId));
    }






















}
