package javase.unit2.t5;

import java.util.*;

/**
 * Created by andrey on 24.02.2017.
 */
public class DisciplineGroupsContainer {

    Map<Discipline, Map<Student, List<Mark>>> disciplineGroups;

    DisciplineGroupsContainer() {
        disciplineGroups = new HashMap<>();
        for (Discipline discipline: Discipline.values()) {
            disciplineGroups.put(discipline, new HashMap<>());
        }
    }

    public DisciplineGroupsContainer add(Discipline discipline, Student student) {
        disciplineGroups.get(discipline).put(student, new ArrayList<>());
        return this;
    }

    public DisciplineGroupsContainer add(Discipline discipline, Student student, List<Mark> marks) {
        disciplineGroups.get(discipline).put(student, marks);
        return this;
    }

    public StudentMarks getMarks(Student student) {
        StudentMarks studentMarks = new StudentMarks();
        for (Discipline discipline: disciplineGroups.keySet()) {
            if (disciplineGroups.get(discipline).containsKey(student)) {
                studentMarks.add(discipline, disciplineGroups.get(discipline).get(student));
            }
        }
        return studentMarks;
    }

    public boolean contains(Discipline discipline, Student student) {
        return disciplineGroups.get(discipline).containsKey(student);
    }

    public void remove(Discipline discipline, Student student) {
        disciplineGroups.get(discipline).remove(student);
    }
}
