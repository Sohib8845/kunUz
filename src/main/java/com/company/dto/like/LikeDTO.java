package com.company.dto.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.company.enums.LikeStatus;
import com.company.enums.LikeType;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private Integer id;
    private LikeStatus status;
    private LocalDateTime createdAd;
    private Integer actionId;
    private LikeType type;
    private Integer profileId;
}
