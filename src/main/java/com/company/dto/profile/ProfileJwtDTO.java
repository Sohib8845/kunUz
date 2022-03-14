package com.company.dto.profile;

import com.company.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileJwtDTO {
    private Integer id;
    private ProfileRole role;
}
