package com.jhipster.crud.service.impl;

import com.jhipster.crud.service.JobSeekerService;
import com.jhipster.crud.domain.JobSeeker;
import com.jhipster.crud.repository.JobSeekerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobSeeker}.
 */
@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final Logger log = LoggerFactory.getLogger(JobSeekerServiceImpl.class);

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public JobSeeker save(JobSeeker jobSeeker) {
        log.debug("Request to save JobSeeker : {}", jobSeeker);
        return jobSeekerRepository.save(jobSeeker);
    }

    @Override
    public Page<JobSeeker> findAll(Pageable pageable) {
        log.debug("Request to get all JobSeekers");
        return jobSeekerRepository.findAll(pageable);
    }


    @Override
    public Optional<JobSeeker> findOne(String id) {
        log.debug("Request to get JobSeeker : {}", id);
        return jobSeekerRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete JobSeeker : {}", id);
        jobSeekerRepository.deleteById(id);
    }
}
