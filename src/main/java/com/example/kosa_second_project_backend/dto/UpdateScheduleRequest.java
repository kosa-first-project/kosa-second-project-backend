package com.example.kosa_second_project_backend.dto;

public class UpdateScheduleRequest {
    private String user_id;
    private String destination;
    private String travel_start_date;
    private String travel_start_time;
    private String travel_end_date;
    private String travel_end_time;
    private String comment;
    private String title;

    // Getters and Setters

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTravel_start_date() {
        return travel_start_date;
    }

    public void setTravel_start_date(String travel_start_date) {
        this.travel_start_date = travel_start_date;
    }

    public String getTravel_start_time() {
        return travel_start_time;
    }

    public void setTravel_start_time(String travel_start_time) {
        this.travel_start_time = travel_start_time;
    }

    public String getTravel_end_date() {
        return travel_end_date;
    }

    public void setTravel_end_date(String travel_end_date) {
        this.travel_end_date = travel_end_date;
    }

    public String getTravel_end_time() {
        return travel_end_time;
    }

    public void setTravel_end_time(String travel_end_time) {
        this.travel_end_time = travel_end_time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UpdateScheduleRequest{" +
                "user_id='" + user_id + '\'' +
                ", destination='" + destination + '\'' +
                ", travel_start_date='" + travel_start_date + '\'' +
                ", travel_start_time='" + travel_start_time + '\'' +
                ", travel_end_date='" + travel_end_date + '\'' +
                ", travel_end_time='" + travel_end_time + '\'' +
                ", comment='" + comment + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
