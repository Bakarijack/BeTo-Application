package com.example.projectfare;

public class ReviewModalClass {
    private String date;
    private String reviewer;
    private int id;
    private String review;

    public ReviewModalClass(int id,String date,String reviewer,String review) {
        this.id = id;
        this.date = date;
        this.reviewer = reviewer;
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
