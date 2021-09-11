package com.group.components.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TimeTable {
    private String day;
    private List<String> timetables;
}