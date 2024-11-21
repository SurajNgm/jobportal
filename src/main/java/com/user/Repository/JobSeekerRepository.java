package com.user.Repository;

import com.user.Model.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeekerProfile, Long> {
    JobSeekerProfile findByUserId(long userId);
}
