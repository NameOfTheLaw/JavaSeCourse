package javase.unit2.t5;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javase.unit2.t5.Discipline.*;
import static org.junit.Assert.*;

public class StudentMarksTest {

    DisciplineGroupsContainer container;
    Student student1;
    Student student2;
    Student student3;

    @Before
    public void beforeTests() {

        container = new DisciplineGroupsContainer();

        student1 = new Student(1, "Viktor Ivanov");
        student2 = new Student(2, "Petr Ivanov");
        student3 = new Student(3, "Nikita Petrov");

        List<Mark> marksList1 = new ArrayList<>();
        marksList1.add(MATH.markOf(5));
        marksList1.add(MATH.markOf(5));
        marksList1.add(MATH.markOf(4));
        marksList1.add(MATH.markOf(5));
        container.add(MATH, student1, marksList1)
                .add(MATH, student2);

        List<Mark> marksList2 = new ArrayList<>();
        marksList2.add(ENGLISH.markOf(3));
        marksList2.add(ENGLISH.markOf(2.1));
        marksList2.add(ENGLISH.markOf(3.2));
        marksList2.add(ENGLISH.markOf(3.8));
        marksList2.add(ENGLISH.markOf(2.9));
        container.add(ENGLISH, student1, marksList2)
                .add(ENGLISH, student3);
    }


    @Test
    public void testGetDisciplines() {
        StudentMarks student1Marks = container.getMarks(student1);

        List<Discipline> studentDisciplines = student1Marks.getDisciplines();

        assertTrue(studentDisciplines.contains(MATH));
        assertTrue(studentDisciplines.contains(ENGLISH));
        assertFalse(studentDisciplines.contains(HISTORY));
    }

    @Test
    public void testGetMarks() {

        StudentMarks student1Marks = container.getMarks(student1);

        List<Mark> mathMarks = student1Marks.getMarks(MATH);

        assertArrayEquals(
                new int[] {5, 5, 4, 5},
                mathMarks.stream()
                        .mapToInt(x -> x.getValue().intValue())
                        .toArray()
        );

        assertArrayEquals(
                new double[] {3, 2.1, 3.2, 3.8, 2.9},
                student1Marks.getMarks(ENGLISH).stream()
                        .mapToDouble(x -> x.getValue().doubleValue())
                        .toArray(),
                0.1d
        );

    }

    @Test
    public void testStudentMarksGetMarksAndSorted() {
        StudentMarks student1Marks = container.getMarks(student1).sorted();
        assertEquals(MATH, student1Marks.getDisciplines().get(0));
        assertEquals(ENGLISH, student1Marks.getDisciplines().get(1));
    }

    @Test
    public void testGetMap() {

        StudentMarks student1Marks = container.getMarks(student1);

        Set<Discipline> studentDisciplines = student1Marks.getMap().keySet();

        assertTrue(studentDisciplines.contains(MATH));
        assertTrue(studentDisciplines.contains(ENGLISH));
        assertFalse(studentDisciplines.contains(HISTORY));
    }

}