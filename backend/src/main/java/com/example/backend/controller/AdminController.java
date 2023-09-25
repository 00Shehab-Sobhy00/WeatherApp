package com.example.backend.controller;


import com.example.backend.dto.request.RegisterAdminDTO;
import com.example.backend.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private final SuperAdminService Service;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("")
    public ResponseEntity<List<RegisterAdminDTO>> getAllAdmins() {
        logger.info("getAllAdmins ()");
        return ResponseEntity.ok(Service.getAllAdmins());
    }

    @GetMapping("/{adminName}")
    public ResponseEntity<RegisterAdminDTO> getAdminByName(@PathVariable String adminName) {

     logger.info("getAdminByName()");
     logger.debug("Admin Name {}",adminName);
        return ResponseEntity.ok(Service.findAdminByName(adminName));
    }


    @PostMapping("")
    public ResponseEntity<RegisterAdminDTO> addAdmin(@RequestBody RegisterAdminDTO admin) {
        logger.info("addAdmin()");
        logger.debug("RegisterAdminDTO {}",admin);
        return ResponseEntity.ok(Service.addAdmin(admin));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<RegisterAdminDTO> updateAdmin(@PathVariable Integer adminId, @RequestBody RegisterAdminDTO admin) {
        logger.info("updateAdmin()");
        logger.debug("RegisterAdminDTO {}",admin);

        return ResponseEntity.ok(Service.updateAdmin(adminId, admin));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer adminId) {
        Service.deleteAdmin(adminId);
        logger.info("deleteAdmin()");
        logger.debug("Admin Id {}",adminId);

        return ResponseEntity.ok("Admin with id " + adminId + " deleted successfully ");
    }

}
