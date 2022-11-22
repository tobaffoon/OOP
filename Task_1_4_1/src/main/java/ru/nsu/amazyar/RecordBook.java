package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecordBook {

    private Map<String, List<Record>> records;
    private int qualificationWorkGrade;

    private enum Grade {
        FAIL, PASS, POOR, SATISFACTORY, GOOD, EXCELLENT
    }

    public enum AssessmentForm {
        CREDIT, DIFFERENTIAL_CREDIT, EXAM
    }

    private class Record {
        private final String teacher;
        private final Grade grade;
        private final AssessmentForm form;

        public Record(String teacher, Grade grade, AssessmentForm form) {
            this.teacher = teacher;
            this.grade = grade;
            this.form = form;
        }
    }

    public void addRecord(String name, boolean pass) {

    }

    public void addRecord(String discipline, String teacher, int grade, AssessmentForm form)
        throws IndexOutOfBoundsException, IllegalStateException {
        // Credited discipline must not have integer as the grade
        if(form == AssessmentForm.CREDIT){
            throw new IllegalStateException("Inappropriate value for assessment form");
        }

        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        switch (grade) {
            case 2:
                previousRecords.add(new Record(teacher, Grade.POOR, form));
                records.putIfAbsent(teacher, previousRecords);
                break;
            case 3:
                previousRecords.add(new Record(teacher, Grade.SATISFACTORY, form));
                records.putIfAbsent(teacher, previousRecords);
                break;
            case 4:
                previousRecords.add(new Record(teacher, Grade.GOOD, form));
                records.putIfAbsent(teacher, previousRecords);
                break;
            case 5:
                previousRecords.add(new Record(teacher, Grade.EXCELLENT, form));
                records.putIfAbsent(teacher, previousRecords);
                break;
            default:
                throw new IndexOutOfBoundsException("Grade out of bounds");
        }
    }
}
