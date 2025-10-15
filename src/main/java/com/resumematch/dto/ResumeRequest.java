package com.resumematch.dto;

public class ResumeRequest {
    private String name;
    private String email;
    private String file;

    public ResumeRequest() {}

    public ResumeRequest(String name, String email, String file) {
        this.name = name;
        this.email = email;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}