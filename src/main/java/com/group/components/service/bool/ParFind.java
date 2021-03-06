package com.group.components.service.bool;

import com.group.components.dao.TimeTablesDao;
import com.group.components.repo.TimeTable;
import com.group.components.service.Pars;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Getter
public class ParFind {

    public boolean isSubgroup(String[] parseSplit, String typeOfTimeTable, String subgroup, String subgroupWithNumber, String numberSubgroup) {
        return isTimeTable(2, typeOfTimeTable, parseSplit) && (isTimeTable(0, subgroup, parseSplit)
                || isTimeTable(0, subgroupWithNumber, parseSplit)
                || isTimeTable(0, numberSubgroup, parseSplit));
    }

    public boolean isTimeTable(int i, String typeOfTimeTable, String[] parseSplit) {
        return parseSplit[i].toLowerCase().contains(typeOfTimeTable);
    }

    public String getPars(String day, String info, TimeTablesDao timeTables) {
        for (TimeTable timetable : timeTables.getTimeTables()) {
            int counter = 0;
            for (String str : timetable.getTimetables()) {
                ++counter;
                if (day.contains(timetable.getDay()) && counter == Integer.parseInt(info) && !str.contains("-")) {
                    return "Сейчас будет : " + str + " " + Pars.getPar(counter - 1) + " " + "пара";
                } else if (day.contains(timetable.getDay()) && counter == Integer.parseInt(info) && str.contains("-")) {
                    return "Сейчас пары не будет ";
                } else if ((day.contains("суббота") || day.contains("воскресенье"))) {
                    return "Сегодня выходной, пар нет, чильте ";
                }
            }
        }
        return "Неправильный формат данных";
    }
}