package com.user.Service;

import com.user.Model.User;
import com.user.Model.UserProfileImage;
import com.user.Repository.UserProfileImageRepository;
import com.user.Repository.UserRepository;
import com.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileImageRepository userProfileImageRepository;
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsersExcept(String email) {
        return userRepository.findAll().stream()
                .filter(user -> !user.getUsername
                        ().equals(email))
                .toList();
    }


    public void saveProfileImage(long userId, MultipartFile file) throws IOException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        UserProfileImage userProfileImage = userProfileImageRepository.findByUserId(userId);
        if (userProfileImage == null) {
            userProfileImage = new UserProfileImage();
            userProfileImage.setUserId(userId);
        }

        userProfileImage.setProfileImage(file.getBytes());
        userProfileImageRepository.save(userProfileImage);
    }

    public UserProfileImage getProfileImage(long userId) {
        return userProfileImageRepository.findByUserId(userId);
    }

    public List<User> allUser(){
        return userRepository.findAll();
    }

    public User AddUser( User user){
        userRepository.save(user);
        return user;
    }
    public User findUser(String userid,String password){
        User u=  userRepository.findByUseridAndPassword(userid,password);
        return u;
    }
    public String changePassword(long id ,String password){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(password);
            userRepository.save(user);
            return "password changed";
            // Save the updated user object back to the repository
        } else {
            // Handle the case where the user with the given id is not found
            return "something went wrong";
        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);

    }

    public List<User> getJobSeekers() {
        return userRepository.findByRole("jobseeker");
    }
    public List<User> getEmployers() {
        return userRepository.findByRole("employer");
    }
}