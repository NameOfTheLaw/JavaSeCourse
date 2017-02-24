package javase.unit2.t5;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by andrey on 24.02.2017.
 */
public class DisciplineGroupsTest {

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
        marksList2.add(Discipline.ENGLISH.markOf(2.9));
        container.add(Discipline.ENGLISH, student1, marksList2)
                .add(Discipline.ENGLISH, student3);
    }

    @Test(expected = NullPointerException.class)
    public void testAddIfArgsIsNull() {
        container.add(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddIfDisciplineIsNull() {
        container.add(null, new Student(15, "noname"));
    }

    @Test(expected = NullPointerException.class)
    public void testAddIfStudentIsNull() {
        container.add(Discipline.MATH, null);
    }

    @Test
    public void testAddWithoutMarks() {
        Student student = new Student(15, "noname");
        assertFalse(container.contains(Discipline.MATH, student));
        container.add(Discipline.MATH, student);
        assertTrue(container.contains(Discipline.MATH, student));
    }

    @Test
    public void testAddWithMarks() {
        List<Mark> marksList = new ArrayList<>();
        marksList.add(Discipline.ENGLISH.markOf(3));
        marksList.add(Discipline.ENGLISH.markOf(2.1));
        marksList.add(Discipline.ENGLISH.markOf(3.2));
        marksList.add(Discipline.ENGLISH.markOf(3.8));
        marksList.add(Discipline.ENGLISH.markOf(2.9));
        container.add(Discipline.MATH, new Student(15, "noname"), marksList);
    }

    @Test
    public void testAddWithMarksIfMarksIsNull() {
        container.add(Discipline.MATH, new Student(15, "noname"), null);
    }

    @Test
    public void testRemove() {
        Student student = new Student(15, "noname");
        container.add(Discipline.MATH, student);
        assertTrue(container.contains(Discipline.MATH, student));
        container.remove(Discipline.MATH, student);
        assertFalse(container.contains(Discipline.MATH, student));
    }

    @Test
    public void testRemoveTwoTimes() {
        Student student = new Student(15, "noname");
        container.add(Discipline.MATH, student);
        assertTrue(container.contains(Discipline.MATH, student));
        container.remove(Discipline.MATH, student);
        assertFalse(container.contains(Discipline.MATH, student));
        container.remove(Discipline.MATH, student);
        assertFalse(container.contains(Discipline.MATH, student));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfArgsIsNull() {
        container.remove(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfStudentIsNull() {
        container.remove(Discipline.MATH, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfDisciplineIsNull() {
        container.remove(null, new Student(15, "noname"));
    }

    @Test
    public void testStudentMarks_GetDisciplines() {
        StudentMarks student1Marks = container.getMarks(student1);

        List<Discipline> studentDisciplines = student1Marks.getDisciplines();
        List<Mark> mathMarks = student1Marks.getMarks(Discipline.MATH);

        assertTrue(studentDisciplines.contains(Discipline.MATH));
        assertTrue(studentDisciplines.contains(Discipline.ENGLISH));
        assertFalse(studentDisciplines.contains(Discipline.HISTORY));
    }

    @Test
    public void testStudentMarks_GetMarks() {

        StudentMarks student1Marks = container.getMarks(student1);

        List<Mark> mathMarks = student1Marks.getMarks(Discipline.MATH);

        assertArrayEquals(
                new int[] {5, 5, 4, 5},
                mathMarks.stream()
                        .mapToInt(x -> x.getValue().intValue())
                        .toArray()
        );

        assertArrayEquals(
                new double[] {3, 2.1, 3.2, 3.8, 2.9},
                student1Marks.getMarks(Discipline.ENGLISH).stream()
                        .mapToDouble(x -> x.getValue().doubleValue())
                        .toArray(),
                0.1d
        );

    }

    @Test
    public void testStudentMarks_GetMarksAndSorted() {
        StudentMarks student1Marks = container.getMarks(student1).sorted();
        assertEquals(Discipline.MATH, student1Marks.getDisciplines().get(0));
        assertEquals(Discipline.ENGLISH, student1Marks.getDisciplines().get(1));
    }

    @Test
    public void testStudentMarks_GetMap() {

        StudentMarks student1Marks = container.getMarks(student1);
        student1Marks.getMap().get(Discipline.MATH);

        Set<Discipline> studentDisciplines = student1Marks.getMap().keySet();

        assertTrue(studentDisciplines.contains(Discipline.MATH));
        assertTrue(studentDisciplines.contains(Discipline.ENGLISH));
        assertFalse(studentDisciplines.contains(Discipline.HISTORY));

    }

}
