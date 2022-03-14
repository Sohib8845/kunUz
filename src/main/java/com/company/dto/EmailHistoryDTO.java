package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.company.enums.EmailStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private Integer id;
    private String fromAccount;
    private String toAccount;
    private LocalDateTime createdAt;
    private EmailStatus status;
    private LocalDateTime usedAt;
}
