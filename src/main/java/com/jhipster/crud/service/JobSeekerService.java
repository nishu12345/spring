package com.jhipster.crud.service;

import com.jhipster.crud.domain.JobSeeker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link JobSeeker}.
 */
public interface JobSeekerService {

    /**
     * Save a jobSeeker.
     *
     * @param jobSeeker the entity to save.
     * @return the persisted entity.
     */
    JobSeeker save(JobSeeker jobSeeker);

    /**
     * Get all the jobSeekers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobSeeker> findAll(Pageable pageable);


    /**
     * Get the "id" jobSeeker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobSeeker> findOne(String id);

    /**
     * Delete the "id" jobSeeker.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
