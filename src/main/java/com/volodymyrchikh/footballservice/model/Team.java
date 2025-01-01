package com.volodymyrchikh.footballservice.model;

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
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Double balance;
    private Double commissionRate;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "player_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players;

}