package com.company.entity;

import com.company.enums.ArticleStatus;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseEntity{
    @Column
    private String title;
    @Column
    private String content;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    // stataus
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ArticleStatus status;
}
