package com.example.kosa_second_project_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id", nullable = false)
    private String user_id;
    private String title;
    private String destination;
    private Date startDateTime;
    private Date endDateTime;
    private String comment;

    public ScheduleEntity(String user_id, String title, String destination, Date startDateTime, Date endDateTime, String comment){
        this.user_id = user_id;
        this.title = title;
        this.destination = destination;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.comment = comment;
    }

    public ScheduleEntity(String user_id, String title, String destination, Date startDateTime, Date endDateTime){
        this.user_id = user_id;
        this.title = title;
        this.destination = destination;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString(){
        return startDateTime + " ~ " + endDateTime + "\n" + destination + "\n" + comment + "\n" + user_id + "\n" + title;
    }
}
