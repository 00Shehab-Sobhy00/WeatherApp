package com.example.backend.service;

import com.example.backend.dto.request.RegisterAdminDTO;
import com.example.backend.dto.request.RegisterUserDTO;
import com.example.backend.model.entity.User;

import java.util.List;

public interface SuperAdminService {

    List<RegisterAdminDTO> getAllAdmins();

    RegisterAdminDTO findAdminByName(String name);

    RegisterAdminDTO addAdmin(RegisterAdminDTO adminUser);

    RegisterAdminDTO updateAdmin ( int userAdminId  , RegisterAdminDTO adminUser);

    void deleteAdmin (Integer userAdminId);

}
