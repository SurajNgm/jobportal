package com.user.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;
    private String location;
    private int experience;
    private String description;

    @ManyToOne
    @JoinColumn(name = "employer_profile_id")
    private EmployerProfile employerProfile;

    @ManyToMany(mappedBy = "appliedJobs", fetch = FetchType.LAZY)
    private Set<JobSeekerProfile> applicant;
}
