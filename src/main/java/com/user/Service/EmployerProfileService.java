package com.user.Service;

import com.user.Model.EmployerProfile;
import com.user.Model.User;
import com.user.Repository.EmployerRepository;
import com.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployerProfileService {
    @Autowired
    private EmployerRepository employerProfileRepository;
    @Autowired
    private UserRepository userRepository;

    public EmployerProfile createOrUpdateEmployerProfile(long userId, EmployerProfile employerProfile) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }

        EmployerProfile existingProfile = employerProfileRepository.findByUserId(userId);

        if (existingProfile != null) {
            existingProfile.setWebSiteUrl(employerProfile.getWebSiteUrl());
            existingProfile.setAddress(employerProfile.getAddress());
            existingProfile.setDescription(employerProfile.getDescription());
            existingProfile.setEstablished(employerProfile.getEstablished());
            // Handle jobs if necessary
            return employerProfileRepository.save(existingProfile);
        } else {
            employerProfile.setUser(user);
            return employerProfileRepository.save(employerProfile);
        }
    }
    public EmployerProfile getEmployerProfile(long userId){
        EmployerProfile employerProfile=employerProfileRepository.findByUserId(userId);
        return  employerProfile;
    }

    }
