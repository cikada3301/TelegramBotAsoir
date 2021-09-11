package com.group.components.dao.impl;

import com.group.components.dao.TimeTablesDao;
import com.group.components.repo.TimeTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
@AllArgsConstructor
public class LowerForFirstTimeTableDaoImpl implements TimeTablesDao {
    private List<TimeTable> timeTables;

    @PostConstruct
    public void init() {
        setTimeTables();
    }

    @Override
    public void setTimeTables() {
        timeTables.add(new TimeTable("понедельник", Arrays.asList("-", "ООП, 111 ауд. корпус 2", "МО, 416 ауд. корпус 2", "-", "-")));
        timeTables.add(new TimeTable("вторник", Arrays.asList("-", "ОАУ 406 ауд. корпус 7", "МО ауд. 229 корпус 1")));
        timeTables.add(new TimeTable("среда", Arrays.asList("СВЧВС 519 ауд. корпус 2", "СКТ 231 ауд. корупс 1", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2")));
        timeTables.add(new TimeTable("четверг", Arrays.asList("ОАУ 404 ауд. корпус 2", "СТК ауд. 406 корпус 7", "СА 409 ауд. корпус 2")));
        timeTables.add(new TimeTable("пятница", Arrays.asList("-", "МО 111 ауд. корпус 2", "Культурология 301 ауд. корпус 1")));
    }
}

