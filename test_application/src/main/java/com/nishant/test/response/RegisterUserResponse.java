package com.nishant.test.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserResponse implements Serializable {
    private static final long serialVersionUID = 959927103889497919L;

    private Long id;

    private String error;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegisterUserResponse [");
        sb.append("id=").append(id);
        sb.append(", error= ").append(error);
        sb.append("]");
        return sb.toString();
    }
}
