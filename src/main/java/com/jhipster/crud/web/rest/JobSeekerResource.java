package com.jhipster.crud.web.rest;

import com.jhipster.crud.domain.JobSeeker;
import com.jhipster.crud.service.JobSeekerService;
import com.jhipster.crud.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.jhipster.crud.domain.JobSeeker}.
 */
@RestController
@RequestMapping("/api")
public class JobSeekerResource {

    private final Logger log = LoggerFactory.getLogger(JobSeekerResource.class);

    private static final String ENTITY_NAME = "jobSeeker";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobSeekerService jobSeekerService;

    public JobSeekerResource(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    /**
     * {@code POST  /job-seekers} : Create a new jobSeeker.
     *
     * @param jobSeeker the jobSeeker to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobSeeker, or with status {@code 400 (Bad Request)} if the jobSeeker has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-seekers")
    public ResponseEntity<JobSeeker> createJobSeeker(@RequestBody JobSeeker jobSeeker) throws URISyntaxException {
        log.debug("REST request to save JobSeeker : {}", jobSeeker);
        if (jobSeeker.getId() != null) {
            throw new BadRequestAlertException("A new jobSeeker cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobSeeker result = jobSeekerService.save(jobSeeker);
        return ResponseEntity.created(new URI("/api/job-seekers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /job-seekers} : Updates an existing jobSeeker.
     *
     * @param jobSeeker the jobSeeker to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobSeeker,
     * or with status {@code 400 (Bad Request)} if the jobSeeker is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobSeeker couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-seekers")
    public ResponseEntity<JobSeeker> updateJobSeeker(@RequestBody JobSeeker jobSeeker) throws URISyntaxException {
        log.debug("REST request to update JobSeeker : {}", jobSeeker);
        if (jobSeeker.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobSeeker result = jobSeekerService.save(jobSeeker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobSeeker.getId()))
            .body(result);
    }

    /**
     * {@code GET  /job-seekers} : get all the jobSeekers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobSeekers in body.
     */
    @GetMapping("/job-seekers")
    public ResponseEntity<List<JobSeeker>> getAllJobSeekers(Pageable pageable) {
        log.debug("REST request to get a page of JobSeekers");
        Page<JobSeeker> page = jobSeekerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-seekers/:id} : get the "id" jobSeeker.
     *
     * @param id the id of the jobSeeker to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobSeeker, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-seekers/{id}")
    public ResponseEntity<JobSeeker> getJobSeeker(@PathVariable String id) {
        log.debug("REST request to get JobSeeker : {}", id);
        Optional<JobSeeker> jobSeeker = jobSeekerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobSeeker);
    }

    /**
     * {@code DELETE  /job-seekers/:id} : delete the "id" jobSeeker.
     *
     * @param id the id of the jobSeeker to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-seekers/{id}")
    public ResponseEntity<Void> deleteJobSeeker(@PathVariable String id) {
        log.debug("REST request to delete JobSeeker : {}", id);
        jobSeekerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
