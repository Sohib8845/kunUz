package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;

    @NotNull(message = "Name can not be empty")
    private String name;
    @NotEmpty(message = "Where surname")
    private String surname;
    @NotBlank
    private String login;
    private String pswd;

    @Email(message = "Email not valid")
    private String email;
    private ProfileRole role;

    private String jwt;    //tooken(id ni korinishi)
}
