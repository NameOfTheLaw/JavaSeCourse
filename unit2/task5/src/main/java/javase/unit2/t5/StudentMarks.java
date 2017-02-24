package javase.unit2.t5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andrey on 24.02.2017.
 */
public class StudentMarks {

    Map<Discipline, List<Mark>> marks = new HashMap<>();

    public StudentMarks() {}

    public StudentMarks(Map<Discipline, List<Mark>> marks) {
        this.marks = marks;
    }

    public StudentMarks add(Discipline discipline, List<Mark> marksList) {
        if (marks.get(discipline) == null) {
            marks.put(discipline, marksList);
        } else {
            marks.get(discipline).addAll(marksList);
        }
        return this;
    }

    public List<Discipline> getDisciplines() {
        return marks.keySet().stream()
                .collect(Collectors.toList());
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
            return averageMark1.compareTo(averageMark2);
        };
    }

    public List<Mark> getMarks(Discipline discipline) {
        return marks.get(discipline);
    }
}
