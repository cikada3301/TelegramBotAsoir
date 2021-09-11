package com.group.components.controllers;

import com.group.components.dao.TimeTablesDao;
import com.group.components.repo.TimeTable;
import com.group.components.service.date.CurrentDate;
import com.group.components.service.TimeTableService;
import com.group.components.service.bool.ParFind;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.LocalTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SpringBot extends TelegramLongPollingBot {

    private final TimeTableService timeTableService;

    private final CurrentDate currentDate;

    private final ParFind parFind;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    private static final String SPACE_AND_COMMA = "[\\s,]+";

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String[] parseSplit = msg.getText().split(SPACE_AND_COMMA);
        LocalTime now = LocalTime.now();
        if (now.getHour() == 9 && now.getMinute() == 0) {
            displayAllLessons(update, "первой", timeTableService.getTimeTables().get(2));
            displayAllLessons(update, "второй", timeTableService.getTimeTables().get(3));
        }
        else {
            displayLessonOfSchedule(update, parseSplit);
        }
    }

    private void displayAllLessons(Update update, String str, TimeTablesDao timeTables) {
        for (TimeTable timeTable : timeTables.getTimeTables()) {
            int counter = 0;
            for (String string : timeTable.getTimetables()) {
                ++counter;
                if (!string.contains("-") && currentDate.getDayOfWeek().contains(timeTable.getDay())) {
                    try {
                        execute(new SendMessage().setChatId(update.getMessage().getChatId())
                                .setText("Сегодня " + counter + " пара " + " у " + str + " подгруппы " + timeTable.getTimetables().get(counter - 1)));
                    } catch (TelegramApiException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    private void displayLessonOfSchedule(Update update, String[] parseSplit) {
        if (parFind.isSubgroup(parseSplit, "верх", "первая подгруппа", "1 подгруппа", "1")) {
            getTableTime(update, currentDate.getDayOfWeek(), parseSplit[1].trim(), timeTableService.getTimeTables().get(2));
        } else if (parFind.isSubgroup(parseSplit, "верх", "вторая подгруппа", "2 подгруппа", "2")) {
            getTableTime(update, currentDate.getDayOfWeek(), parseSplit[1].trim(), timeTableService.getTimeTables().get(3));
        } else if (parFind.isSubgroup(parseSplit, "низ", "первая подгруппа", "1 подгруппа", "1")) {
            getTableTime(update, currentDate.getDayOfWeek(), parseSplit[1].trim(), timeTableService.getTimeTables().get(0));
        } else if (parFind.isSubgroup(parseSplit, "низ", "вторая подгруппа", "2 подгруппа", "2")) {
            getTableTime(update, currentDate.getDayOfWeek(), parseSplit[1].trim(), timeTableService.getTimeTables().get(1));
        }
    }

    private void getTableTime(Update update, String day, String par, TimeTablesDao timeTables) {
        getMessage(update, parFind.getPars(day, par, timeTables));
    }
    public void getMessage(Update update, String message) {
        try {
            execute(new SendMessage().setChatId(update.getMessage().getChatId())
                    .setText(message));
        }
        catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
}
