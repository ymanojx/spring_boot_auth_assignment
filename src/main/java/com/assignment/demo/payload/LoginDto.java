package com.assignment.demo.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty
    private String usernameOrEmail;
    @NotEmpty
    @Size(min = 8, message = "password should be of size at least 8")
    private String password;
}
