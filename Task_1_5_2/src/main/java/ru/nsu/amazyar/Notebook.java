package ru.nsu.amazyar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection of Notes.
 * <p>
 * Can add, remove and edit notes. Is not serializable, but notes are.
 * </p>
 * <p>
 * The notes are order ascending by creation time. The order is preserved as linked list is used to
 * store notes and new ones are added with current time by default. When a note is added with set
 * time the order isn't preserved.
 * </p>
 */
public class Notebook {

    private final List<Note> notes;
    private final DateTimeFormatter notebookFormatter = DateTimeFormatter.ofPattern(
        "dd.MM.uuuu H:mm");

    /**
     * Default constructor.
     */
    public Notebook() {
        notes = new LinkedList<>();
    }

    /**
     * Add new note by its characteristics. Adds only if note with similar name doesn't exist
     *
     * @param name    note's name
     * @param content contents of the note
     * @return true if note was added (it's name is unique in notebook), false otherwise
     */
    public boolean add(String name, String content) {
        return add(new Note(name, content, LocalDateTime.now()));
    }

    /**
     * Add new note.
     *
     * @return true if note was added (it's name is unique in notebook), false otherwise
     */
    public boolean add(Note note) {
        if (this.find(note.getName()) != null) {
            return false;
        }

        notes.add(note);
        return true;
    }

    /**
     * Remove note by name.
     *
     * @param name note's name
     * @return true if note was removed, false if no note with such name was found
     */
    public boolean remove(String name) {
        Note existingNote = this.find(name);
        if (existingNote == null) {
            return false;
        } else {
            notes.remove(existingNote);
            return true;
        }
    }

    /**
     * Get list of added notes.
     */
    public List<Note> getNotes() {
        return this.notes;
    }

    /**
     * Find a note by name.
     *
     * @param name name of sought note
     * @return found Note if note with such a name exists, null otherwise
     */
    public Note find(String name) {
        for (Note note : notes) {
            if (note.getName().equals(name)) {
                return note;
            }
        }
        return null;
    }

    /**
     * Convert all notes to string.
     */
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Note note : this.getNotes()) {
            result.append(note.toString()).append("\n\n");
        }

        return result.toString();
    }

    /**
     * Get DateTimeFormatter of the notebook.
     */
    public DateTimeFormatter getDateFormatter() {
        return this.notebookFormatter;
    }

    /**
     * Clear list of notes.
     */
    public void clear() {
        this.notes.clear();
    }
}
