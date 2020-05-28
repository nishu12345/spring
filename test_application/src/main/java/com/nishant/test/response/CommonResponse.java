package com.nishant.test.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CommonResponse implements Serializable {

    private static final long serialVersionUID = -1401141450070768987L;

    private Boolean status;

    private String description;

    private Object response;

    @JsonProperty("id")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("response")
    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommonResponse [");
        sb.append("status=").append(status);
        sb.append(", description= ").append(description);
        sb.append(", response=").append(response);
        sb.append("]");
        return sb.toString();
    }
}
