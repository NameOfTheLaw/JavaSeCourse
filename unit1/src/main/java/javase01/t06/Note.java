package javase01.t06;

/**
 * Represents the single note in the notebook {@link javase01.t06.Notebook}
 *
 * Created by andrey on 06.02.2017.
 */
public class Note {

    private String text;

    public Note(String text) {
        this.text = text;
    }

    /**
     * Replace the note's text
     *
     * @param editedText new text for the note
     */
    public void edit(String editedText) {
        this.text = editedText;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
