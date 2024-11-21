package com.user.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobSeekerProfile {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String resume;
    private String skills;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "job_applications",
            joinColumns = @JoinColumn(name = "job_seeker_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id")
    )

    private Set<Job> appliedJobs;
}
