package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true, nullable = false)
    private String login;
    @Column
    private String pswd;
    @Column(unique = true)
    private String email;
    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @OneToMany(mappedBy = "profile",fetch = FetchType.LAZY)
    private List<ArticleEntity> articleEntityList;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProfileStatus status;

}
