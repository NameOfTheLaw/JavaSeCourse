package javase.unit2.t5;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static javase.unit2.t5.Discipline.*;

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
        container.add(MATH, null);
    }

    @Test
    public void testAddWithoutMarks() {
        Student student = new Student(15, "noname");
        assertFalse(container.contains(MATH, student));
        container.add(MATH, student);
        assertTrue(container.contains(MATH, student));
    }

    @Test
    public void testAddWithMarks() {
        List<Mark> marksList = new ArrayList<>();
        marksList.add(ENGLISH.markOf(3));
        marksList.add(ENGLISH.markOf(2.1));
        marksList.add(ENGLISH.markOf(3.2));
        marksList.add(ENGLISH.markOf(3.8));
        marksList.add(ENGLISH.markOf(2.9));
        container.add(MATH, new Student(15, "noname"), marksList);
    }

    @Test
    public void testAddWithMarksIfMarksIsNull() {
        container.add(MATH, new Student(15, "noname"), null);
    }

    @Test
    public void testRemove() {
        Student student = new Student(15, "noname");
        container.add(MATH, student);
        assertTrue(container.contains(MATH, student));
        container.remove(MATH, student);
        assertFalse(container.contains(MATH, student));
    }

    @Test
    public void testRemoveTwoTimes() {
        Student student = new Student(15, "noname");
        container.add(MATH, student);
        assertTrue(container.contains(MATH, student));
        container.remove(MATH, student);
        assertFalse(container.contains(MATH, student));
        container.remove(MATH, student);
        assertFalse(container.contains(MATH, student));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfArgsIsNull() {
        container.remove(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfStudentIsNull() {
        container.remove(MATH, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveIfDisciplineIsNull() {
        container.remove(null, new Student(15, "noname"));
    }

    @Test
    public void testGetStudentsByDiscipline() {
        Set<Student> studentsGroup = container.getStudentsByDiscipline(MATH);
        assertTrue(studentsGroup.contains(student1));
        assertTrue(studentsGroup.contains(student2));
        assertFalse(studentsGroup.contains(student3));

        studentsGroup = container.getStudentsByDiscipline(ENGLISH);
        assertTrue(studentsGroup.contains(student1));
        assertTrue(studentsGroup.contains(student3));
        assertFalse(studentsGroup.contains(student2));
    }

    @Test(expected = NullPointerException.class)
    public void testGetStudentsByDisciplineWithNullArg() {
        Set<Student> studentsGroup = container.getStudentsByDiscipline(null);
    }

    @Test
    public void testGetStudentsByDisciplineWhereOutputIsEmpty() {
        Set<Student> studentsGroup = container.getStudentsByDiscipline(HISTORY);
        assertTrue(studentsGroup.isEmpty());
    }

    @Test
    public void testGetStudentsByDisciplineWithMarksCheckingAreAllStudentsInOutput() {
        Map<Student, List<Mark>> studentsGroup = container.getStudentsWithMarksByDiscipline(MATH);
        Set<Student> studentsSet = studentsGroup.keySet();
        assertTrue(studentsSet.contains(student1));
        assertTrue(studentsSet.contains(student2));
        assertFalse(studentsSet.contains(student3));
    }

    @Test
    public void testGetStudentsByDisciplineWithMarksCheckingAreStudentsHaveCorrectMarks() {
        Map<Student, List<Mark>> studentsGroup = container.getStudentsWithMarksByDiscipline(MATH);

        assertArrayEquals(
                new int[] {5, 5, 4, 5},
                studentsGroup.get(student1).stream()
                        .mapToInt(x -> x.getValue().intValue())
                        .toArray()
        );
    }

    @Test(expected = NullPointerException.class)
    public void testGetStudentsByDisciplineWithNullArgWithMarks() {
        Map<Student, List<Mark>> studentsGroup = container.getStudentsWithMarksByDiscipline(null);
    }

    @Test
    public void testGetStudentsByDisciplineWhereOutputIsEmptyWithMarks() {
        Map<Student, List<Mark>> studentsGroup = container.getStudentsWithMarksByDiscipline(HISTORY);
        assertTrue(studentsGroup.isEmpty());
    }

}
