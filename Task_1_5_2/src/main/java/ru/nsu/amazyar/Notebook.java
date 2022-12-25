package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Notebook{
    private final List<Note> notes;

    public Notebook() {
        notes = new LinkedList<>();
    }

    public void add(String name, String content){
        if(this.contains(name) != null){
            return;
        }

        Note newNote = new Note(name, content, LocalDateTime.now());
        notes.add(newNote);
    }

    public boolean remove(String name){
        Note existingNote = this.contains(name);
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

    private Note contains(String name){
        for (Note note : notes) {
            if(note.getName().equals(name)){
                return note;
            }
        }
        return null;
    }
}
