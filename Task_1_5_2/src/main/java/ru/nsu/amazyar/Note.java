package ru.nsu.amazyar;

import java.time.LocalDateTime;

public class Note {
    private final String name;
    private final String content;
    private final LocalDateTime creationTime;

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
}
