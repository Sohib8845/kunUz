package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RegistrationDTO {

    private Integer id;
    @NotNull
    @NotEmpty(message = "Mazgi name qani")
    private String name;
    @Size(min=3,max = 20,message = "Isn't in limit")
    private String surname;
    @NotBlank(message = "Login incorect")
    private String login;
    private String pswd;
    @Email(message = "Email not valid")
    private String email;

}
