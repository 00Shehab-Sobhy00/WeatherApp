package com.example.backend.controller;

import com.example.backend.dto.EditeUserDetailsDTO;
import com.example.backend.dto.UserInfoDTO;
import com.example.backend.service.UserAndAdminDetailsService;
import com.example.backend.service.impl.NotesServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminDetailsController {

    private final UserAndAdminDetailsService userAndAdminDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AdminDetailsController.class);

    @GetMapping("/admin/profile/{adminId}")
    public ResponseEntity<UserInfoDTO> getAdminDetails(@PathVariable Integer adminId) {
        logger.info("getAdminDetails()");
        logger.debug("Admin Id : {}",adminId);
        return ResponseEntity.ok(userAndAdminDetailsService.viewAdminDetails(adminId));
    }

    @PutMapping("/admin/profile/{adminId}")
    public ResponseEntity<EditeUserDetailsDTO> updateAdminDetails(
            @PathVariable Integer adminId, @RequestBody EditeUserDetailsDTO adminDetails) {
        logger.info("updateAdminDetails()");
        logger.debug("Admin Id : {}",adminId);
        return ResponseEntity.ok(userAndAdminDetailsService.updateAdminDetails( adminDetails,adminId));
    }
}
