package com.user.Controller;

import com.user.Model.Job;
import com.user.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("company/{empId}")
    public ResponseEntity<Job> addJob(@PathVariable long empId, @RequestBody Job job) {
        Job updateJob = jobService.createJob(empId, job);
        return ResponseEntity.ok(updateJob);
    }

    @GetMapping("jobseeker/{jobSeekerProfileId}/{jobId}")
    public ResponseEntity<String> applyForJob(@PathVariable long jobSeekerProfileId, @PathVariable long jobId) {
        try {
            jobService.applyForJob(jobSeekerProfileId, jobId);
            return ResponseEntity.ok("Application successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        try {
            List<Job> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("company/find/posted/{empId}")
    public ResponseEntity<List<Job>> findPostedJob(@PathVariable long empId) {
        try {
            List<Job> jobs = jobService.getPostedJobs(empId);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("company/remove/{jobId}")
    public String deleteJob(@PathVariable long jobId){
        return jobService.removeJob(jobId);

    }
}