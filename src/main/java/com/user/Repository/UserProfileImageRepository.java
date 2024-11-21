package com.user.Repository;

import com.user.Model.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
    UserProfileImage findByUserId(long userId);
}
