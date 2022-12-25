package ru.nsu.amazyar;

import java.time.LocalDateTime;

public class Note {
    private String name;
    private String content;
    private LocalDateTime creationTime;

    public Note(String name, String content, LocalDateTime creationTime) {
        this.name = name;
        this.content = content;
        this.creationTime = creationTime;
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
}
