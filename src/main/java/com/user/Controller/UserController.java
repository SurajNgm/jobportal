package com.user.Controller;

import com.user.DTO.EmployeeDto;
import com.user.DTO.JobSeekerDto;
import com.user.DTO.Password;
import com.user.DTO.UserDTO;
import com.user.Model.EmployerProfile;
import com.user.Model.JobSeekerProfile;
import com.user.Model.User;
import com.user.Model.UserProfileImage;
import com.user.Service.EmployerProfileService;
import com.user.Service.JobSeekerProfileService;
import com.user.Service.UserService;
import com.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JobSeekerProfileService jobSeekerProfileService;
    @Autowired
    private EmployerProfileService employerProfileService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersExcept(@RequestParam String username) {
        List<User> users = userService.findAllUsersExcept(username);
        return ResponseEntity.ok(users);
    }


    @PostMapping("/changePassword/{id}")
    public String changePassword(@PathVariable int id, @RequestBody Password password) {
        return userService.changePassword(id, password.getPassword());
    }

    @PostMapping("/{id}/uploadProfileImage")
    public ResponseEntity<String> uploadProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        try {
            userService.saveProfileImage(id, file);
            return ResponseEntity.ok("Profile image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile image");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/profileImage")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable int id) {
        UserProfileImage profileImage = userService.getProfileImage(id);
        if (profileImage != null && profileImage.getProfileImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(profileImage.getProfileImage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("admin/AllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.allUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/company/getjobseekers")
    public ResponseEntity<List<JobSeekerDto>> getJobSeekers() {
        List<User> users = userService.getJobSeekers();
        List<JobSeekerDto> validJobSeekers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            JobSeekerProfile jsp = jobSeekerProfileService.getJobSeekerProfile(users.get(i).getId());
            if (jsp != null) {
                JobSeekerDto jsd = new JobSeekerDto();
                jsd.setName(users.get(i).getFirstName() + " " + users.get(i).getLastName());
                jsd.setEmail(users.get(i).getEmail());
                jsd.setResumeLink(jsp.getResume());
                jsd.setSkills(jsp.getSkills());
                validJobSeekers.add(jsd);
            }
        }
        return ResponseEntity.ok(validJobSeekers);
    }


    @GetMapping("/jobseeker/getAlEmployers")
    public ResponseEntity<List<EmployeeDto>> getEmployers() {
        List<User> users = userService.getEmployers();
        List<EmployeeDto> validEmployers = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            EmployerProfile employerProfile = employerProfileService.getEmployerProfile(users.get(i).getId());
            if (employerProfile != null) {
                EmployeeDto employerDto = new EmployeeDto();
                employerDto.setName(users.get(i).getFirstName() + " " + users.get(i).getLastName());
                employerDto.setEmail(users.get(i).getEmail());
                employerDto.setAddress(employerProfile.getAddress());
                employerDto.setWebSiteUrl(employerProfile.getWebSiteUrl());
                validEmployers.add(employerDto);
            }
        }
        return ResponseEntity.ok(validEmployers);
    }

}

