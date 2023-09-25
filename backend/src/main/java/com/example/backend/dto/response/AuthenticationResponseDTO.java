package com.example.backend.dto.response;


import com.example.backend.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
//    private User user ;
    private Integer userId;
//   private String role;
//    (inside the token )
    private String token;

}
