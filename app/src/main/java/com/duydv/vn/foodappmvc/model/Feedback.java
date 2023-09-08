package com.duydv.vn.foodappmvc.model;

import java.io.Serializable;

public class Feedback implements Serializable {
    private String email;
    private String feedback;

    public Feedback() {
    }

    public Feedback(String email, String feedback) {
        this.email = email;
        this.feedback = feedback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
