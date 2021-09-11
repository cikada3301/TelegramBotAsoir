package com.group.components.service.impl;

import com.group.components.dao.TimeTablesDao;
import com.group.components.service.TimeTableService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {
    private List<TimeTablesDao> timeTables;
}

