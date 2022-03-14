package com.company.entity;

import com.company.enums.EmailStatus;
import lombok.Getter;
import lombok.Setter;
import com.company.enums.EmailStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String fromAccount;
    @Column
    private String toAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;
    @Column
    private LocalDateTime usedAt;
}
