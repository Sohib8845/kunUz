package com.company.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "comment")
public class CommentEntity extends BaseEntity {
    @Column
    private String content;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")

    private ArticleEntity article;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE}, fetch = FetchType.LAZY)

    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
