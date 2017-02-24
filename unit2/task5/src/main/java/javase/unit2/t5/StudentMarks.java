package javase.unit2.t5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class represents a progres report of the current student.
 *
 * Class is the shell for map of {@link Discipline} as key and a list of {@link Mark} as value.
 *
 * Created by andrey on 24.02.2017.
 */
public class StudentMarks {

    Map<Discipline, List<Mark>> marks = new HashMap<>();

    public StudentMarks() {}

    public StudentMarks(Map<Discipline, List<Mark>> marks) {
        this.marks = marks;
    }

    /**
     * Adds list of marks to a list of specific discipline.
     *
     * @param discipline
     * @param marksList
     * @return
     */
    public StudentMarks add(Discipline discipline, List<Mark> marksList) {
        if (marks.get(discipline) == null) {
            marks.put(discipline, marksList);
        } else {
            marks.get(discipline).addAll(marksList);
        }
        return this;
    }

    /**
     * Returns list of discipline in which student takes part of.
     *
     * @return
     */
    public List<Discipline> getDisciplines() {
        return marks.keySet().stream()
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of Marks for the current discipline
     *
     * @param discipline
     * @return
     */
    public List<Mark> getMarks(Discipline discipline) {
        return marks.get(discipline);
    }

    /**
     * Returns a discipline-marks map.
     *
     * @return
     */
    public Map<Discipline, List<Mark>> getMap() {
        return marks;
    }

    public StudentMarks sorted() {
        Map sortedMarks = new TreeMap<Discipline, List<Mark>>(getDisciplineComparator());
        sortedMarks.putAll(marks);
        return new StudentMarks(sortedMarks);
    }

    private Comparator<Discipline> getDisciplineComparator() {
        return (discipline1, discipline2) -> {
            Double averageMark1 = marks.get(discipline1).stream()
                    .mapToDouble(x -> x.getValue().doubleValue())
                    .average()
                    .getAsDouble();
            Double averageMark2 = marks.get(discipline2).stream()
                    .mapToDouble(x -> x.getValue().doubleValue())
                    .average()
                    .getAsDouble();
            return -averageMark1.compareTo(averageMark2);
        };
    }
}
