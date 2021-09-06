package com.group.components.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class Subgroup {
   /* @Id
    private String id;
    @Field("subjects")
    private List<String> subjects;

    public Subgroup() {}

    public Subgroup(String id, List<String> subjects) {
        this.id = id;
        this.subjects = subjects;
    }
    public String getId() {
        return id;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return "Подгруппа " + id + "Пара " + subjects.toString() ;
    }*/
}
