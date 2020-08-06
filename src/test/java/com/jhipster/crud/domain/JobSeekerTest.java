package com.jhipster.crud.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jhipster.crud.web.rest.TestUtil;

public class JobSeekerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSeeker.class);
        JobSeeker jobSeeker1 = new JobSeeker();
        jobSeeker1.setId("id1");
        JobSeeker jobSeeker2 = new JobSeeker();
        jobSeeker2.setId(jobSeeker1.getId());
        assertThat(jobSeeker1).isEqualTo(jobSeeker2);
        jobSeeker2.setId("id2");
        assertThat(jobSeeker1).isNotEqualTo(jobSeeker2);
        jobSeeker1.setId(null);
        assertThat(jobSeeker1).isNotEqualTo(jobSeeker2);
    }
}
