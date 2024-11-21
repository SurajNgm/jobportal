package com.user.Service;

import com.user.Model.EmployerProfile;
import com.user.Model.Job;
import com.user.Model.JobSeekerProfile;
import com.user.Repository.EmployerRepository;
import com.user.Repository.JobRepository;
import com.user.Repository.JobSeekerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    public Job createJob(long empId,Job job) {
        Optional<EmployerProfile> optionalEmployerProfile=employerRepository.findById(empId);
        EmployerProfile emp=optionalEmployerProfile.get();
        job.setEmployerProfile(emp);

        Job jb=jobRepository.save(job);
        System.out.println(jb);
        return jb;

    }

    @Transactional
    public void applyForJob(long jobSeekerProfileId, long jobId) {
        Optional<JobSeekerProfile> jobSeekerProfileOpt = jobSeekerRepository.findById(jobSeekerProfileId);
        Optional<Job> jobOpt = jobRepository.findById(jobId);

        if (jobSeekerProfileOpt.isPresent() && jobOpt.isPresent()) {
            JobSeekerProfile jobSeekerProfile = jobSeekerProfileOpt.get();
            Job job = jobOpt.get();

            jobSeekerProfile.getAppliedJobs().add(job);


            job.getApplicant().add(jobSeekerProfile);

            jobSeekerRepository.save(jobSeekerProfile);
            jobRepository.save(job);
        } else {

            throw new RuntimeException("Job or Job Seeker Profile not found");
        }
    }

    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }
    public List<Job> getPostedJobs(long empId){
        return jobRepository.findByEmployeeProfileId(empId);
    }
    public String removeJob(long jobId){
        try{
            jobRepository.deleteById(jobId);
            return "Successfull";
        }
        catch(Exception e){
            return "Something went wrong";
        }
    }
}
