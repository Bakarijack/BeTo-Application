package com.example.projectfare;

public class EstablishmentModal {
    private String establishmentName;
    private String establishmentType;
    private String establishmentLocation;
    private String reviewComment;
    private String foodType;
    private String establishmentId;
    private int reviewId;
    private String reviewer;
    private String reviewDate;
    private String loveCount;
    private String likeCount;
    private String hateCount;
    private String myReaction;
    private byte[] imageBytes;
//
//    public EstablishmentModal(String establishmentName, String establishmentType, String establishmentLocation, String reviewComment, String foodType) {
//        this.establishmentName = establishmentName;
//        this.establishmentType = establishmentType;
//        this.establishmentLocation = establishmentLocation;
//        this.reviewComment = reviewComment;
//        this.foodType = foodType;
//    }

    public EstablishmentModal(String establishmentName,String establishmentType, String reviewComment){
        this.establishmentName = establishmentName;
        this.establishmentType = establishmentType;
        this.reviewComment = reviewComment;
    }

    public EstablishmentModal(String name,String type,String food,String estlocation,int reviewId,String reviewer,String reviewDate,String reviewComment,String loveCount,String likeCount,String hateCount,String myReaction,byte[] imageBytes){
        this.establishmentName = name;
        this.establishmentType = type;
        this.foodType = food;
        this.establishmentLocation = estlocation;
        this.reviewId = reviewId;
        this.reviewer = reviewer;
        this.reviewDate = reviewDate;
        this.reviewComment = reviewComment;
        this.loveCount = loveCount;
        this.likeCount = likeCount;
        this.hateCount = hateCount;
        this.myReaction = myReaction;
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getMyReaction() {
        return myReaction;
    }

    public void setMyReaction(String myReaction) {
        this.myReaction = myReaction;
    }

    public String getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(String loveCount) {
        this.loveCount = loveCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getHateCount() {
        return hateCount;
    }

    public void setHateCount(String hateCount) {
        this.hateCount = hateCount;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(String establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }

    public String getEstablishmentLocation() {
        return establishmentLocation;
    }

    public void setEstablishmentLocation(String establishmentLocation) {
        this.establishmentLocation = establishmentLocation;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
