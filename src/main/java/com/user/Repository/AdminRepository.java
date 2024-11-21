package com.user.Repository;

import com.user.Model.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminProfile, Long> {
}
