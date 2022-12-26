package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Notebook{
    private final List<Note> notes;
    private final DateTimeFormatter notebookFormatter = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy hh:mm",
        Locale.ENGLISH);

    public Notebook() {
        notes = new LinkedList<>();
    }

    public void add(String name, String content){
        if(this.find(name) != null){
            return;
        }

        Note newNote = new Note(name, content, LocalDateTime.now(), notebookFormatter);
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
            result.append(note.toString()).append("\n\n");
        }

        return result.toString();
    }

    public DateTimeFormatter getDateFormatter(){
        return this.notebookFormatter;
    }
}
