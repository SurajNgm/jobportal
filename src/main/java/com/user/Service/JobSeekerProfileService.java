package com.user.Service;

import com.user.Model.JobSeekerProfile;
import com.user.Model.User;
import com.user.Repository.AdminRepository;
import com.user.Repository.JobSeekerRepository;
import com.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {
    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public JobSeekerProfile CreateOrUpdateJobSeeker(long userId,JobSeekerProfile jobSeekerProfile){
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

         JobSeekerProfile existingProfile = jobSeekerRepository.findByUserId(userId);

        if (existingProfile != null) {
            existingProfile.setSkills(jobSeekerProfile.getSkills());
            existingProfile.setResume(jobSeekerProfile.getResume());
            // Handle jobs if necessary
            return jobSeekerRepository.save(existingProfile);
        } else {
            jobSeekerProfile.setUser(user);
            return jobSeekerRepository.save(jobSeekerProfile);
        }
    }
    public JobSeekerProfile getJobSeekerProfile(long userId){
        JobSeekerProfile jobSeekerProfile=jobSeekerRepository.findByUserId(userId);
        return  jobSeekerProfile;

    }
}
