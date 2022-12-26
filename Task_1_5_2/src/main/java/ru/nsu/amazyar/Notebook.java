package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Notebook{
    private final List<Note> notes;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy hh:mm",
        Locale.ENGLISH);

    public Notebook() {
        notes = new LinkedList<>();
    }

    public void add(String name, String content){
        if(this.find(name) != null){
            return;
        }

        Note newNote = new Note(name, content, LocalDateTime.now());
        notes.add(newNote);
    }

    public void add(Note note){
        if(this.find(note.getName()) != null){
            return;
        }

        notes.add(note);
    }

    public boolean remove(String name){
        Note existingNote = this.find(name);
        if(existingNote == null){
            return false;
        } else{
            notes.remove(existingNote);
            return true;
        }
    }

    public List<Note> getNotes(){
        return this.notes;
    }

    public Note find(String name){
        for (Note note : notes) {
            if(note.getName().equals(name)){
                return note;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();

        for (Note note : this.getNotes()) {
            result.append("Name: ").append(note.getName()).append("\n");
            result.append("Note: ").append(note.getContent()).append("\n");
            result.append("Added: ").append(note.getCreationTime().format(DATE_TIME_FORMATTER)).append("\n\n");
        }

        return result.toString();
    }

    public DateTimeFormatter getDateFormatter(){
        return this.DATE_TIME_FORMATTER;
    }
}
