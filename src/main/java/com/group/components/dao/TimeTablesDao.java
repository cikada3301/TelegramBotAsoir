package com.group.components.dao;

import com.group.components.repo.TimeTable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTablesDao {
    void setTimeTables();
    List<TimeTable> getTimeTables();
}
