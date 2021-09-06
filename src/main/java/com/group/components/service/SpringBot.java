package com.group.components.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class SpringBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;
    private final String spaceAndComma = "[\\s,]+";

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    Map<String, List<String>> subjectsToLowerForFirstSubroup = new TreeMap<String, List<String>>();
    Map<String, List<String>> subjectsToLowerForSecondSubroup = new TreeMap<String, List<String>>();

    Map<String, List<String>> subjectsToUpperForFirstSubroup = new TreeMap<String, List<String>>();
    Map<String, List<String>> subjectsToUpperForSecondSubroup = new TreeMap<String, List<String>>();

    @Override
    public void onUpdateReceived(Update update) {
        setMapOfSubjects();
        Message msg = update.getMessage();
        String[] parseSplit = msg.getText().split(spaceAndComma);
        System.out.println(msg);
        if (parseSplit[2].toLowerCase().contains("верх") && (parseSplit[0].toLowerCase().contains("первая подгруппа")
                || parseSplit[0].toLowerCase().contains("1 подгруппа")
                || parseSplit[0].toLowerCase().contains("1"))) {
            getTableTime(update, getDayOfWeek(), parseSplit[1].trim(), subjectsToUpperForFirstSubroup);
        } else if (parseSplit[2].toLowerCase().contains("верх") && (parseSplit[0].toLowerCase().contains("вторая подгруппа")
                || parseSplit[0].toLowerCase().contains("2 подгруппа")
                || parseSplit[0].toLowerCase().contains("2"))) {
            getTableTime(update, getDayOfWeek(), parseSplit[1].trim(), subjectsToUpperForSecondSubroup);
        }
        else if (parseSplit[2].toLowerCase().contains("низ") && (parseSplit[0].toLowerCase().contains("первая подгруппа")
                || parseSplit[0].toLowerCase().contains("1 подгруппа")
                || parseSplit[0].toLowerCase().contains("1"))) {
            getTableTime(update, getDayOfWeek(), parseSplit[1].trim(), subjectsToLowerForFirstSubroup);
        }
        else if (parseSplit[2].toLowerCase().contains("низ") && (parseSplit[0].toLowerCase().contains("вторая подгруппа")
                || parseSplit[0].toLowerCase().contains("2 подгруппа")
                || parseSplit[0].toLowerCase().contains("2"))) {
            getTableTime(update, getDayOfWeek(), parseSplit[1].trim(), subjectsToLowerForSecondSubroup);
        }
    }

    private void getTableTime(Update update, String day, String par, Map<String, List<String>> subjectsToLower) {
        String[] info = par.split(spaceAndComma);
        for (Map.Entry<String, List<String>> item : subjectsToLower.entrySet()) {
            getPars(update, day, info[0], item);
        }
    }

    private void getPars(Update update, String day, String info, Map.Entry<String, List<String>> item) {
        int counter = 0;
        for (String str : item.getValue()) {
            ++counter;
            try {
                if (day.contains(item.getKey()) && counter == Integer.parseInt(info) && !str.contains("-")) {
                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Сейчас будет : " + str + " " + Pars.getPar(--counter) + " " + "пара"));
                    break;
                } else if (counter == Integer.parseInt(info) && (day.contains("субббота") || day.contains("воскресенье"))) {
                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Сегодня выходной, пар нет, чильте "));
                    break;
                } else if (counter == Integer.parseInt(info) & str.contains("-")) {
                    execute(new SendMessage().setChatId(update.getMessage().getChatId())
                            .setText("Сейчас пары не будет "));
                    break;
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String getDayOfWeek() {
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Locale localeRu = new Locale("ru", "RU");
        return dayOfWeek.getDisplayName(TextStyle.FULL, localeRu);

    }

    private void setMapOfSubjects() {
        subjectsToLowerForFirstSubroup.put("понедельник", Arrays.asList("-", "ООП, 111 ауд. корпус 2", "МО, 416 ауд. корпус 2", "-", "-"));
        subjectsToLowerForFirstSubroup.put("вторник", Arrays.asList("-", "ОАУ 406 ауд. корпус 7", "МО ауд. 229 корпус 1"));
        subjectsToLowerForFirstSubroup.put("среда", Arrays.asList("СВЧВС 519 ауд. корпус 2", "СКТ 231 ауд. корупс 1", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2"));
        subjectsToLowerForFirstSubroup.put("четверг", Arrays.asList("ОАУ 404 ауд. корпус 2", "СТК ауд. 406 корпус 7", "СА 409 ауд. корпус 2"));
        subjectsToLowerForFirstSubroup.put("пятница", Arrays.asList("-", "МО 111 ауд. корпус 2", "Культурология 301 ауд. корпус 1"));
        subjectsToLowerForSecondSubroup.put("понедельник", Arrays.asList("-", "ООП, 111 ауд. корпус 2", "ООП 517 ауд.корпус 2", "СА 233 ауд, корпус 1", "СВЧВС 519 ауд. корпус 2"));
        subjectsToLowerForSecondSubroup.put("вторник", Arrays.asList("-", "ОАУ 406 ауд. корпус 7", "-"));
        subjectsToLowerForSecondSubroup.put("среда", Arrays.asList("СА 223 ауд. корпус 1", "ОАУ 207 ауд. корупс 2", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2"));
        subjectsToLowerForSecondSubroup.put("четверг", Arrays.asList("ООП 449 ауд. корпус 1", "-", "СА 409 ауд. корпус 2"));
        subjectsToLowerForSecondSubroup.put("пятница", Arrays.asList("СТК 231 ауд. корпус 1", "МО 111 ауд. корпус 2", "Культурология 301 ауд. корпус 1"));

        subjectsToUpperForFirstSubroup.put("понедельник", Arrays.asList("-", "-", "МО, 416 ауд. корпус 2", "ООП 517 ауд.корпус 2", "СА 233 ауд, корпус 1", "СВЧВС 519 ауд. корпус 2"));
        subjectsToUpperForFirstSubroup.put("вторник", Arrays.asList("Культурология 308 ауд. корпус 1", "ОАУ 406 ауд. корпус 7", "-"));
        subjectsToUpperForFirstSubroup.put("среда", Arrays.asList("СА 223 ауд. корпус 1", "ОАУ 207 ауд. корупс 2", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2"));
        subjectsToUpperForFirstSubroup.put("четверг", Arrays.asList("ООП 449 ауд. корпус 1", "-", "СА 409 ауд. корпус 2"));
        subjectsToUpperForFirstSubroup.put("пятница", Arrays.asList("СТК 231 ауд. корпус 1", "МО 111 ауд. корпус 2", "-"));
        subjectsToUpperForSecondSubroup.put("понедельник", Arrays.asList("-", "-", "ООП 517 ауд.корпус 2", "-", "-"));
        subjectsToUpperForSecondSubroup.put("вторник", Arrays.asList("Культурология 308 ауд. корпус 1", "ОАУ 406 ауд. корпус 7", "МО ауд. 229 корпус 1"));
        subjectsToUpperForSecondSubroup.put("среда", Arrays.asList("СВЧВС 519 ауд. корпус 2", "СКТ 231 ауд. корупс 1", "СВЧВС 301 ауд. корпус 2", "СТК 412 ауд. корпус 2"));
        subjectsToUpperForSecondSubroup.put("четверг", Arrays.asList("ОАУ 404 ауд. корпус 2", "СТК ауд. 406 корпус 7", "СА 409 ауд. корпус 2"));
        subjectsToUpperForSecondSubroup.put("пятница", Arrays.asList("-", "МО 111 ауд. корпус 2", "-"));
    }
}
