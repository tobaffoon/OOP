package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Note in notebook.
 */
public class Note {

    private String name;
    private String content;
    private LocalDateTime creationTime;
    private final DateTimeFormatter localFormatter;
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy H:mm",
        Locale.ENGLISH);

    /**
     * Constructor for deserialization.
     */
    public Note() {
        name = "None";
        content = "None";
        creationTime = LocalDateTime.now();
        localFormatter = DEFAULT_FORMATTER;
    }

    /**
     * Normal constructor.
     */
    public Note(@NotNull String name, @NotNull String content,
        @NotNull LocalDateTime creationTime) {
        this(name, content, creationTime, DEFAULT_FORMATTER);
    }

    /**
     * Constructor with formatter for date and time provided.
     */
    public Note(@NotNull String name, @NotNull String content, @NotNull LocalDateTime creationTime,
        @NotNull DateTimeFormatter formatter) {
        this.name = name;
        this.content = content;
        this.creationTime = creationTime;
        this.localFormatter = formatter;
    }

    /**
     * Check if note was created between given points in time.
     */
    public boolean isBetween(@NotNull LocalDateTime from, @NotNull LocalDateTime to) {
        return creationTime.compareTo(from) >= 0 && creationTime.compareTo(to) <= 0;
    }

    /**
     * Check if note contains all keywords in its name.
     *
     * @param keywords array of keywords.
     */
    public boolean nameContains(@NotNull String[] keywords) {
        for (String key : keywords) {
            if (!this.getName().toLowerCase().contains(key.toLowerCase())) {
                return false;
            }
        }
        return true;
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

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public void setCreationTime(@NotNull LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * toString override.
     */
    public String toString() {
        return "Name: " + this.getName() + "\n"
            + "Note: " + this.getContent() + "\n"
            + "Added: " + this.getCreationTime().format(localFormatter);
    }
}