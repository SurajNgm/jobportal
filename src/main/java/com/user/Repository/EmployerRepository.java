package com.user.Repository;

import com.user.Model.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<EmployerProfile, Long> {

    EmployerProfile findByUserId(long userId);
}
