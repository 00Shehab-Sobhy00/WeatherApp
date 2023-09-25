package com.example.backend.service.impl;

import com.example.backend.dto.request.RegisterAdminDTO;
import com.example.backend.exceptions.InternalErrorsException;
import com.example.backend.exceptions.UserAlreadyExistException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import com.example.backend.model.repo.UserRepo;
import com.example.backend.service.SuperAdminService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(SuperAdminServiceImpl.class);

    @Override
    public List<RegisterAdminDTO> getAllAdmins() {
        logger.info("getAllAdmins()");
        List<User> allUserAdmins = userRepo.findByRoles(ERole.ADMIN);
        List<RegisterAdminDTO> allAdmins = new ArrayList<>();

        for (User user : allUserAdmins) {
            RegisterAdminDTO adminDTO = new RegisterAdminDTO();
            adminDTO.setAdminId(String.valueOf(user.getId()));
            adminDTO.setName(user.getName());
            adminDTO.setEmail(user.getEmail());
            adminDTO.setPassword(user.getPassword());
            adminDTO.setPhone(user.getPhone());
            allAdmins.add(adminDTO);
        }
        return allAdmins;
    }

    @Override
    public RegisterAdminDTO findAdminByName(String adminName) {
        logger.info("findAdminByName()");
        logger.debug("adminName: {}", adminName);
        Optional<User> adminOptional = userRepo.findByName(adminName);
        if (adminOptional.isPresent()) {
            RegisterAdminDTO foundAdmin = new RegisterAdminDTO();
            foundAdmin.setAdminId(String.valueOf(adminOptional.get().getId()));
            foundAdmin.setName(adminOptional.get().getName());
            foundAdmin.setEmail(adminOptional.get().getEmail());
            foundAdmin.setPassword(adminOptional.get().getPassword());
            foundAdmin.setPhone(adminOptional.get().getPhone());
            return foundAdmin;
        } else
            logger.error("there is no admin of name: {}", adminName);
        throw new UserNotFoundException(" there is no admin of name " + adminName);
    }


    @Override
    public RegisterAdminDTO addAdmin(RegisterAdminDTO adminUser) {
        logger.info("addAdmin()");
        logger.debug("RegisterAdminDTO: {}", adminUser);
        var userAdmin = new User();
        if (userRepo.findByEmail(adminUser.getEmail()).isPresent()){
            logger.error("Email already exsist: {}", adminUser.getEmail());
            throw new UserAlreadyExistException("Email already exsist");
        }

        else{

            userAdmin.setName(adminUser.getName());
            userAdmin.setEmail(adminUser.getEmail());
            userAdmin.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            userAdmin.setPhone(adminUser.getPhone());
            userAdmin.setRoles(ERole.ADMIN);
            userRepo.save(userAdmin);

            var addAdmin = new RegisterAdminDTO();
            addAdmin.setAdminId(String.valueOf(userAdmin.getId()));
            addAdmin.setName(userAdmin.getName());
            addAdmin.setEmail(userAdmin.getEmail());
            addAdmin.setPhone(adminUser.getPhone());
            addAdmin.setPassword(passwordEncoder.encode(adminUser.getPassword()));
            return addAdmin;
        }


    }

    @Override
    public RegisterAdminDTO updateAdmin(int userAdminId, RegisterAdminDTO adminUser) {
        logger.info("updateAdmin()");
        logger.debug("userAdminId: {} , RegisterAdminDTO: {}", userAdminId, adminUser);
        Optional<User> getAdUser = userRepo.findById(userAdminId);
        if (getAdUser.isPresent()) {
            getAdUser.get().setId(Integer.parseInt(adminUser.getAdminId()));
            getAdUser.get().setName(adminUser.getName());
            getAdUser.get().setEmail(adminUser.getEmail());
            getAdUser.get().setPassword(passwordEncoder.encode(adminUser.getPassword()));
            getAdUser.get().setPhone(adminUser.getPhone());
            getAdUser.get().setRoles(ERole.ADMIN);
            userRepo.save(getAdUser.get());


            var updatedAd = new RegisterAdminDTO();

            updatedAd.setAdminId(String.valueOf(getAdUser.get().getId()));
            updatedAd.setName(getAdUser.get().getName());
            updatedAd.setEmail(getAdUser.get().getEmail());
            updatedAd.setPhone(getAdUser.get().getPhone());
            updatedAd.setPassword(passwordEncoder.encode(getAdUser.get().getPassword()));

            return updatedAd;
        }
        logger.error("User id =" + userAdminId + " not found ");
        throw new UserNotFoundException("User id" + userAdminId + " not found");
    }

    @Override
    public void deleteAdmin(Integer userAdminId) {
        logger.info("deleteAdmin()");
        logger.debug("adminId: {}", userAdminId);
        Optional<User> deletedAdUser = userRepo.findById(userAdminId);
        if (deletedAdUser.isPresent()) {
            if (deletedAdUser.get().getRoles().name().equals(ERole.ADMIN.name())) {
                userRepo.delete(deletedAdUser.get());
            } else {
                logger.error("Id " + userAdminId + " its not an id of admin ");
                throw new UserNotFoundException("Id " + userAdminId + "its not an id of admin ");
            }

        } else{
            logger.error("there is no admin of id: {}", userAdminId);
            throw new UserNotFoundException(" there is no admin of id = " + userAdminId);
        }

    }
}
