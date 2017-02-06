package javase01.t06;

/**
 * Represents the list of notes {@link javase01.t06.Note}
 *
 * Created by andrey on 06.02.2017.
 */
public class Notebook {

    private static final double expandCoefficient = 1.5;
    private Note[] notes;
    private int size;

    public Notebook() {
        this(new Note[10], 0);
    }

    public Notebook(Note[] notes) {
        this.notes = notes;
        this.size = notes.length;
    }

    public Notebook(Note[] notes, int size) {
        this.notes = notes;
        this.size = size;
    }

    /**
     * Adds a note to the notebook
     *
     * @param note
     */
    public void addNote(Note note) {
        ensureCapasity(size + 1);
        notes[size] = note;
        size += 1;
    }

    /**
     * Removes note from the notebook
     *
     * @param i note index
     */
    public void removeNote(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        for (int j = i; j < size-1; j++) {
            notes[j] = notes[j+1];
        }
        size -= 1;
    }

    /**
     * Removes all notes from the notebook where references are equal to the given.
     *
     * @param noteToRemove
     */
    public void removeNote(Note noteToRemove) {
        if (noteToRemove == null) {
            throw new NullPointerException();
        }
        int i = 0;
        while (i < size) {
            if (noteToRemove == notes[i]) {
                removeNote(i);
            } else {
                i += 1;
            }
        }
    }

    /**
     * Returns note by the index
     *
     * @param i
     * @return
     */
    public Note getNote(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return notes[i];
    }

    /**
     * Prints all notes to console
     */
    public void printAllNotes() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ") " + notes[i]);
            System.out.println();
        }
    }

    /**
     * Returns list of all notes
     *
     * @return array of notes
     */
    public Note[] getAllNotes() {
        return getNotesArrayCopy(size); //Can be replaced by System.arrayCopy()
    }

    /**
     * Returns the number of notes
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    private void ensureCapasity(int requiredCapasity) {
        if (requiredCapasity <= getCapacity()) {
            return;
        }
        int newCapacity = Math.max(requiredCapasity, (int) (getCapacity()*expandCoefficient));
        Note[] newNotesArray = getNotesArrayCopy(newCapacity); //Can be replaced by System.arrayCopy()
        notes = newNotesArray;
    }

    private int getCapacity() {
        return notes.length;
    }

    private Note[] getNotesArrayCopy(int requiredCapacity) {
        Note[] newNotesArray = new Note[requiredCapacity];
        for (int i = 0; i < size; i++) {
            newNotesArray[i] = this.notes[i];
        }
        return newNotesArray;
    }
}
