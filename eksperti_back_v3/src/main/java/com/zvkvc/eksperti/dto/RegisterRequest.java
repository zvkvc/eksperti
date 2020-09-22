package com.zvkvc.eksperti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest { // data transfer object
    private String username;
    private String email;
    private String password;
}
