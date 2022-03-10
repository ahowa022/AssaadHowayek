package com.example.seg2505_project;

public class Rating {
    private String comment;
    private Double rating;
    public Rating(){}

    public Rating(String comment, Double rating){
        this.comment =comment;
        this.rating = rating;
    }
    public Double getRating(){
        return rating;
    }
    public String getComment(){
        return comment;
    }
    public void setRating(Double number){
        rating = number;
    }

    public void setComment(String newComment){
        comment = newComment;
    }
}
