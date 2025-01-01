package com.volodymyrchikh.footballservice.model;

import com.volodymyrchikh.footballservice.dto.common.PlayerPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private PlayerPosition position;
    private Integer number;
    private Integer experienceInMonths;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "players")
    private Set<Team> teams;

}