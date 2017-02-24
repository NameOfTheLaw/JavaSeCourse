package javase.unit2.t5;

import java.util.*;

/**
 * Class provides a way to contain and handle groups of {@link Student} by {@link Discipline}.
 *
 * Every student has or has not marks on each of the disciplines.
 *
 * Created by andrey on 24.02.2017.
 */
public class DisciplineGroupsContainer {

    private Map<Discipline, Map<Student, List<Mark>>> disciplineGroups;

    DisciplineGroupsContainer() {
        disciplineGroups = new HashMap<>();
        for (Discipline discipline: Discipline.values()) {
            disciplineGroups.put(discipline, new HashMap<>());
        }
    }

    /**
     * Adds the student to the discipline group.
     *
     * @param discipline
     * @param student
     * @return
     */
    public DisciplineGroupsContainer add(Discipline discipline, Student student) {
        if (discipline == null || student == null) throw new NullPointerException();
        disciplineGroups.get(discipline).put(student, new ArrayList<>());
        return this;
    }

    /**
     * Adds the student with his marks to the discipline group.
     *
     * If the student was in a group already then only marks would be added.
     * @param discipline
     * @param student
     * @param marks
     * @return
     */
    public DisciplineGroupsContainer add(Discipline discipline, Student student, List<Mark> marks) {
        disciplineGroups.get(discipline).put(student, marks);
        return this;
    }

    /**
     * Returns a {@link StudentMarks} of the current student.
     *
     * @param student
     * @return
     */
    public StudentMarks getMarks(Student student) {
        StudentMarks studentMarks = new StudentMarks();
        for (Discipline discipline: disciplineGroups.keySet()) {
            if (disciplineGroups.get(discipline).containsKey(student)) {
                studentMarks.add(discipline, disciplineGroups.get(discipline).get(student));
            }
        }
        return studentMarks;
    }

    /**
     * Checks if the student is in the discipline group or not.
     *
     * @param discipline
     * @param student
     * @return
     */
    public boolean contains(Discipline discipline, Student student) {
        return disciplineGroups.get(discipline).containsKey(student);
    }

    /**
     * Removes student from the discipline's group.
     *
     * @param discipline
     * @param student
     */
    public void remove(Discipline discipline, Student student) {
        if (discipline == null || student == null) throw new NullPointerException();
        disciplineGroups.get(discipline).remove(student);
    }
}
