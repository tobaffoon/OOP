package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
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

    public RecordBook() {
        this.records = new HashMap<>();
        this.qualificationWorkGrade = -1;
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

        public String getTeacher() {
            return teacher;
        }

        public Grade getGrade() {
            return grade;
        }

        public AssessmentForm getForm() {
            return form;
        }
    }

    public void addRecord(String discipline, String teacher, boolean pass) {
        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        if (pass) {
            previousRecords.add(new Record(teacher, Grade.PASS, AssessmentForm.CREDIT));
            records.putIfAbsent(teacher, previousRecords);
        } else {
            previousRecords.add(new Record(teacher, Grade.FAIL, AssessmentForm.CREDIT));
        }
    }

    public void addRecord(String discipline, String teacher, int grade, AssessmentForm form)
        throws IndexOutOfBoundsException, IllegalStateException {
        // Credited discipline must not have integer as the grade
        if (form == AssessmentForm.CREDIT) {
            throw new IllegalStateException("Inappropriate value for assessment form");
        }

        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        switch (grade) {
            case 2:
                previousRecords.add(new Record(teacher, Grade.POOR, form));
                records.putIfAbsent(teacher, previousRecords);  // use putIfAbsent because if
                break;                                          // list isn't absent it's updated
            case 3:                                             // by itself upon adding new record
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
