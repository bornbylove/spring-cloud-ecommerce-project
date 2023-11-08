package com.ebenz.Security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @Size(min = 6, max = 20, message = "Username length should not be less than 6 and more than 20 characters")
    private String userName;

    @NotBlank
    @Size(min = 8, message = "Password should not be less than 40 characters")
    private String password;

    @NotBlank
    @Size(max = 25, message = "First name should not be more than 25 characters")
    private String firstName;

    @NotBlank
    @Size(max = 25, message = "Last name should not be more than 25 characters")
    private String lastName;

    @NotBlank
    @Size(max = 40, message = "Email length should not be greater than 40 characters")
    @Email
    private String email;

}
