package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Note {
    private String name;
    private String content;
    private LocalDateTime creationTime;
    private final DateTimeFormatter localFormatter;
    private final static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy hh:mm",
        Locale.ENGLISH);

    public Note(){
        name = "None";
        content = "None";
        creationTime = LocalDateTime.now();
        localFormatter = defaultFormatter;
    }

    public Note(String name, String content, LocalDateTime creationTime) {
        this(name, content, creationTime, defaultFormatter);
    }

    public Note(String name, String content, LocalDateTime creationTime, DateTimeFormatter formatter) {
        this.name = name;
        this.content = content;
        this.creationTime = creationTime;
        this.localFormatter = formatter;
    }

    public boolean isBetween(LocalDateTime from, LocalDateTime to){
        return creationTime.compareTo(from) >= 0 && creationTime.compareTo(to) <= 0;
    }

    public boolean nameContains(String[] keywords){
        for (String key : keywords) {
            if(this.getName().toLowerCase().contains(key.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String toString(){
        return "Name: " + this.getName() + "\n"
            + "Note: " + this.getContent() + "\n"
            + "Added: " + this.getCreationTime().format(localFormatter);
    }
}