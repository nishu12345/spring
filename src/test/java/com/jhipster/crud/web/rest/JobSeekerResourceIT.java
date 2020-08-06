package com.jhipster.crud.web.rest;

import com.jhipster.crud.JhipsterCrudApp;
import com.jhipster.crud.domain.JobSeeker;
import com.jhipster.crud.repository.JobSeekerRepository;
import com.jhipster.crud.service.JobSeekerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.crud.domain.enumeration.Gender;
/**
 * Integration tests for the {@link JobSeekerResource} REST controller.
 */
@SpringBootTest(classes = JhipsterCrudApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobSeekerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Integer DEFAULT_EXPERIENCE = 1;
    private static final Integer UPDATED_EXPERIENCE = 2;

    private static final Float DEFAULT_CTC = 1F;
    private static final Float UPDATED_CTC = 2F;

    private static final Integer DEFAULT_EXPECTED_CTC = 1;
    private static final Integer UPDATED_EXPECTED_CTC = 2;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_RESUME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESUME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESUME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESUME_CONTENT_TYPE = "image/png";

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private MockMvc restJobSeekerMockMvc;

    private JobSeeker jobSeeker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobSeeker createEntity() {
        JobSeeker jobSeeker = new JobSeeker()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .experience(DEFAULT_EXPERIENCE)
            .ctc(DEFAULT_CTC)
            .expectedCTC(DEFAULT_EXPECTED_CTC)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .resume(DEFAULT_RESUME)
            .resumeContentType(DEFAULT_RESUME_CONTENT_TYPE);
        return jobSeeker;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobSeeker createUpdatedEntity() {
        JobSeeker jobSeeker = new JobSeeker()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .experience(UPDATED_EXPERIENCE)
            .ctc(UPDATED_CTC)
            .expectedCTC(UPDATED_EXPECTED_CTC)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE);
        return jobSeeker;
    }

    @BeforeEach
    public void initTest() {
        jobSeekerRepository.deleteAll();
        jobSeeker = createEntity();
    }

    @Test
    public void createJobSeeker() throws Exception {
        int databaseSizeBeforeCreate = jobSeekerRepository.findAll().size();
        // Create the JobSeeker
        restJobSeekerMockMvc.perform(post("/api/job-seekers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isCreated());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeCreate + 1);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobSeeker.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testJobSeeker.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testJobSeeker.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testJobSeeker.getCtc()).isEqualTo(DEFAULT_CTC);
        assertThat(testJobSeeker.getExpectedCTC()).isEqualTo(DEFAULT_EXPECTED_CTC);
        assertThat(testJobSeeker.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testJobSeeker.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testJobSeeker.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testJobSeeker.getResumeContentType()).isEqualTo(DEFAULT_RESUME_CONTENT_TYPE);
    }

    @Test
    public void createJobSeekerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobSeekerRepository.findAll().size();

        // Create the JobSeeker with an existing ID
        jobSeeker.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobSeekerMockMvc.perform(post("/api/job-seekers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllJobSeekers() throws Exception {
        // Initialize the database
        jobSeekerRepository.save(jobSeeker);

        // Get all the jobSeekerList
        restJobSeekerMockMvc.perform(get("/api/job-seekers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSeeker.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE)))
            .andExpect(jsonPath("$.[*].ctc").value(hasItem(DEFAULT_CTC.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedCTC").value(hasItem(DEFAULT_EXPECTED_CTC)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].resumeContentType").value(hasItem(DEFAULT_RESUME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESUME))));
    }
    
    @Test
    public void getJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerRepository.save(jobSeeker);

        // Get the jobSeeker
        restJobSeekerMockMvc.perform(get("/api/job-seekers/{id}", jobSeeker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobSeeker.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE))
            .andExpect(jsonPath("$.ctc").value(DEFAULT_CTC.doubleValue()))
            .andExpect(jsonPath("$.expectedCTC").value(DEFAULT_EXPECTED_CTC))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.resumeContentType").value(DEFAULT_RESUME_CONTENT_TYPE))
            .andExpect(jsonPath("$.resume").value(Base64Utils.encodeToString(DEFAULT_RESUME)));
    }
    @Test
    public void getNonExistingJobSeeker() throws Exception {
        // Get the jobSeeker
        restJobSeekerMockMvc.perform(get("/api/job-seekers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerService.save(jobSeeker);

        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();

        // Update the jobSeeker
        JobSeeker updatedJobSeeker = jobSeekerRepository.findById(jobSeeker.getId()).get();
        updatedJobSeeker
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .experience(UPDATED_EXPERIENCE)
            .ctc(UPDATED_CTC)
            .expectedCTC(UPDATED_EXPECTED_CTC)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE);

        restJobSeekerMockMvc.perform(put("/api/job-seekers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobSeeker)))
            .andExpect(status().isOk());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
        JobSeeker testJobSeeker = jobSeekerList.get(jobSeekerList.size() - 1);
        assertThat(testJobSeeker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobSeeker.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testJobSeeker.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJobSeeker.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testJobSeeker.getCtc()).isEqualTo(UPDATED_CTC);
        assertThat(testJobSeeker.getExpectedCTC()).isEqualTo(UPDATED_EXPECTED_CTC);
        assertThat(testJobSeeker.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testJobSeeker.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testJobSeeker.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testJobSeeker.getResumeContentType()).isEqualTo(UPDATED_RESUME_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingJobSeeker() throws Exception {
        int databaseSizeBeforeUpdate = jobSeekerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobSeekerMockMvc.perform(put("/api/job-seekers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobSeeker)))
            .andExpect(status().isBadRequest());

        // Validate the JobSeeker in the database
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteJobSeeker() throws Exception {
        // Initialize the database
        jobSeekerService.save(jobSeeker);

        int databaseSizeBeforeDelete = jobSeekerRepository.findAll().size();

        // Delete the jobSeeker
        restJobSeekerMockMvc.perform(delete("/api/job-seekers/{id}", jobSeeker.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        assertThat(jobSeekerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
