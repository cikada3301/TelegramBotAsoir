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
public class UpperWeekForFirstSubgroupTimeTableImpl implements TimeTablesDao {
    private List<TimeTable> timeTables;

    @PostConstruct
    public void init() {
        setTimeTables();
    }

    @Override
    public void setTimeTables() {
        timeTables.add(new TimeTable("понедельник", Arrays.asList("-", "-", "МО, 416 ауд. корпус 2", "ООП 517 ауд.корпус 2", "СА 233 ауд, корпус 1", "СВЧВС 519 ауд. корпус 2")));
        timeTables.add(new TimeTable("вторник", Arrays.asList("Культурология 308 ауд. корпус 1", "ОАУ 406 ауд. корпус 7", "-")));
        timeTables.add(new TimeTable("среда", Arrays.asList("СА 223 ауд. корпус 1", "ОАУ 207 ауд. корупс 2", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2")));
        timeTables.add(new TimeTable("четверг", Arrays.asList("ООП 449 ауд. корпус 1", "-", "СА 409 ауд. корпус 2")));
        timeTables.add(new TimeTable("пятница", Arrays.asList("СТК 231 ауд. корпус 1", "МО 111 ауд. корпус 2", "-")));

    }
}
