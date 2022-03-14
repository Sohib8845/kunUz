package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentFilterDTO {
    private Integer coommentId;
    private Integer profileId;
    private Integer articleId;
    private LocalDate fromDate;
    private LocalDate toDate;

}
