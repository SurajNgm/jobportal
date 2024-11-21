package com.user.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployerProfile {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String webSiteUrl;
    private String address;
    private String description;
    private int established;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employerProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Job> jobs;
}

