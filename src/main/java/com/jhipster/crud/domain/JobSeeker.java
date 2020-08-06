package com.jhipster.crud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

import com.jhipster.crud.domain.enumeration.Gender;

/**
 * A JobSeeker.
 */
@Document(collection = "job_seeker")
public class JobSeeker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("age")
    private Integer age;

    @Field("gender")
    private Gender gender;

    @Field("experience")
    private Integer experience;

    @Field("ctc")
    private Float ctc;

    @Field("expected_ctc")
    private Integer expectedCTC;

    @Field("photo")
    private byte[] photo;

    @Field("photo_content_type")
    private String photoContentType;

    @Field("resume")
    private byte[] resume;

    @Field("resume_content_type")
    private String resumeContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public JobSeeker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public JobSeeker age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public JobSeeker gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getExperience() {
        return experience;
    }

    public JobSeeker experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Float getCtc() {
        return ctc;
    }

    public JobSeeker ctc(Float ctc) {
        this.ctc = ctc;
        return this;
    }

    public void setCtc(Float ctc) {
        this.ctc = ctc;
    }

    public Integer getExpectedCTC() {
        return expectedCTC;
    }

    public JobSeeker expectedCTC(Integer expectedCTC) {
        this.expectedCTC = expectedCTC;
        return this;
    }

    public void setExpectedCTC(Integer expectedCTC) {
        this.expectedCTC = expectedCTC;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public JobSeeker photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public JobSeeker photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public byte[] getResume() {
        return resume;
    }

    public JobSeeker resume(byte[] resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public JobSeeker resumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
        return this;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobSeeker)) {
            return false;
        }
        return id != null && id.equals(((JobSeeker) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobSeeker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", experience=" + getExperience() +
            ", ctc=" + getCtc() +
            ", expectedCTC=" + getExpectedCTC() +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", resume='" + getResume() + "'" +
            ", resumeContentType='" + getResumeContentType() + "'" +
            "}";
    }
}
