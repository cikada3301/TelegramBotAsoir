package com.group.components.service;

import com.group.components.dao.TimeTablesDao;

import java.util.List;

public interface TimeTableService {
    List<TimeTablesDao> getTimeTables();
}
