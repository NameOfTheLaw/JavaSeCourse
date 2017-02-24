package javase.unit2.t5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andrey on 24.02.2017.
 */
public class DisciplineGroupsTest {

    @Test
    public void testOfUsage() {
        DisciplineGroupsContainer container = new DisciplineGroupsContainer();

        Student student1 = new Student(1, "Viktor Ivanov");
        Student student2 = new Student(2, "Petr Ivanov");
        Student student3 = new Student(3, "Nikita Petrov");

        List<Mark> marksList1 = new ArrayList<>();
        marksList1.add(Discipline.MATH.markOf(5));
        marksList1.add(Discipline.MATH.markOf(5));
        marksList1.add(Discipline.MATH.markOf(4));
        marksList1.add(Discipline.MATH.markOf(5));
        container.add(Discipline.MATH, student1, marksList1)
                .add(Discipline.MATH, student2);

        List<Mark> marksList2 = new ArrayList<>();
        marksList2.add(Discipline.ENGLISH.markOf(3));
        marksList2.add(Discipline.ENGLISH.markOf(2.1));
        marksList2.add(Discipline.ENGLISH.markOf(3.2));
        marksList2.add(Discipline.ENGLISH.markOf(3.8));
        container.add(Discipline.ENGLISH, student1, marksList2)
                .add(Discipline.ENGLISH, student3);

        assertTrue(container.contains(Discipline.ENGLISH, student3));
        container.remove(Discipline.ENGLISH, student3);
        assertFalse(container.contains(Discipline.ENGLISH, student3));

        StudentMarks student1Marks = container.getMarks(student1)
                .sorted();

        List<Discipline> studentDisciplines = student1Marks.getDisciplines();
        List<Mark> mathMarks = student1Marks.getMarks(Discipline.MATH);
    }
}
