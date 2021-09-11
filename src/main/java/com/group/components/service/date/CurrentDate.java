package com.group.components.service.date;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@AllArgsConstructor
@Component
public class CurrentDate {
    public String getDayOfWeek() {
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Locale localeRu = new Locale("ru", "RU");
        return dayOfWeek.getDisplayName(TextStyle.FULL, localeRu);
    }
}
