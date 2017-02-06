package javase01.t06;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andrey on 06.02.2017.
 */
public class NotebookTest {
    @Test
    public void addNote() throws Exception {
        Notebook notebook = new Notebook();

        assertEquals(0, notebook.getSize());

        Note note1 = new Note("note 1");
        notebook.addNote(note1);
        assertTrue(note1 == notebook.getNote(0));
        assertEquals(1, notebook.getSize());

        Note note2 = new Note("note 2");
        notebook.addNote(note2);
        assertTrue(note2 == notebook.getNote(1));
        assertEquals(2, notebook.getSize());

        notebook.removeNote(0);
        assertTrue(note2 == notebook.getNote(0));
        assertEquals(1, notebook.getSize());

    }

    @Test
    public void removeNoteInt() throws Exception {
        Notebook notebook = new Notebook(new Note[] {new Note("note 1"), new Note("note 2"), new Note("note 3")});

        assertEquals(3, notebook.getSize());

        notebook.removeNote(1);
        assertEquals(2, notebook.getSize());
        notebook.removeNote(0);
        notebook.removeNote(0);
        assertEquals(0, notebook.getSize());
        notebook.addNote(new Note("new note 1"));
        notebook.addNote(new Note("new note 2"));
        assertEquals(2, notebook.getSize());
        notebook.removeNote(1);
        assertEquals(1, notebook.getSize());
    }

    @Test
    public void removeNote() throws Exception {
        Note note1 = new Note("note 1");
        Note note2 = new Note("note 2");
        Note note3 = new Note("note 3");
        Notebook notebook = new Notebook(new Note[] {note1, note2, note3});

        assertEquals(3, notebook.getSize());

        notebook.removeNote(note1);
        assertEquals(2, notebook.getSize());
        assertTrue(note2 == notebook.getNote(0));
        assertTrue(note3 == notebook.getNote(1));

        notebook.addNote(note1);
        assertEquals(3, notebook.getSize());
        assertTrue(note1 == notebook.getNote(2));

        notebook.addNote(note1);
        assertEquals(4, notebook.getSize());
        assertTrue(note1 == notebook.getNote(3));

        notebook.removeNote(note1);
        assertEquals(2, notebook.getSize());
        assertTrue(note3 == notebook.getNote(1));

    }

    @Test
    public void getAllNotes() throws Exception {
        Note note1 = new Note("note 1");
        Note note2 = new Note("note 2");
        Note note3 = new Note("note 3");

        Note[] testNotesArray = new Note[4];
        testNotesArray[0] = note1;
        testNotesArray[1] = note2;
        testNotesArray[2] = note3;
        testNotesArray[3] = new Note("note 4");

        Notebook notebook = new Notebook(new Note[] {note1, note2, note3, new Note("note 4")});
        Note[] notesArray = notebook.getAllNotes();

        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                assertTrue(testNotesArray[i] == notesArray[i]);
            } else {
                assertFalse(testNotesArray[i] == notesArray[i]);
            }
        }

    }

    @Test
    public void getSize() throws Exception {
        Notebook notebook = new Notebook(new Note[] {new Note("note 1"), new Note("note 2"), new Note("note 3")});

        assertEquals(3, notebook.getSize());

        notebook.removeNote(1);
        assertEquals(2, notebook.getSize());

        notebook.removeNote(0);
        assertEquals(1, notebook.getSize());

        notebook.removeNote(0);
        assertEquals(0, notebook.getSize());

        notebook.addNote(new Note("note"));
        assertEquals(1, notebook.getSize());

        notebook.addNote(new Note("note"));
        assertEquals(2, notebook.getSize());

        notebook.removeNote(1);
        assertEquals(1, notebook.getSize());

        notebook = new Notebook();

        assertEquals(0, notebook.getSize());

        notebook.addNote(new Note("note"));
        assertEquals(1, notebook.getSize());
    }

}