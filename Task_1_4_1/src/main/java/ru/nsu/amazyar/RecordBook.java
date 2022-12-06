package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordBook {

    private Map<String, List<Record>> records;
    private int qualificationWorkGrade;

    public enum Grade {
        FAIL(0), PASS(1), POOR(2), SATISFACTORY(3), GOOD(4), EXCELLENT(5);

        public final int intValue;

        Grade(int grade) {
            intValue = grade;
        }

        public int getIntValue(){
            return intValue;
        }
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
        private final int semester;
        private final Grade grade;
        private final AssessmentForm form;

        public Record(String teacher, int semester, Grade grade, AssessmentForm form) {
            this.teacher = teacher;
            this.semester = semester;
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

        public int getSemester() {
            return semester;
        }
    }

    public void addRecord(String discipline, int semester, String teacher, boolean pass) {
        checkSemester(semester);
        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        if (pass) {
            previousRecords.add(new Record(teacher, semester, Grade.PASS, AssessmentForm.CREDIT));
            records.putIfAbsent(teacher, previousRecords);
        } else {
            previousRecords.add(new Record(teacher, semester, Grade.FAIL, AssessmentForm.CREDIT));
        }
    }

    public void addRecord(String discipline, int semester, String teacher, int grade,
        AssessmentForm form)
        throws IndexOutOfBoundsException, IllegalStateException {
        checkSemester(semester);
        // Credited discipline must not have integer as the grade
        if (form == AssessmentForm.CREDIT) {
            throw new IllegalStateException("Inappropriate value for assessment form");
        }

        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        previousRecords.add(new Record(teacher, semester, mapIntToGrade(grade), form));
        //use putIfAbsent because if list isn't absent it's updated by itself upon adding new record
        records.putIfAbsent(teacher, previousRecords);
    }

    public int getQualificationWorkGrade() {
        return qualificationWorkGrade;
    }

    public void setQualificationWorkGrade(int qualificationWorkGrade) {
        this.qualificationWorkGrade = qualificationWorkGrade;
    }

    public double getAverageScore() {
        return records.values().stream()
            .flatMap(List::stream)
            .filter(record -> record.form == AssessmentForm.EXAM
                || record.form == AssessmentForm.DIFFERENTIAL_CREDIT)
            .mapToInt(record -> mapGradeToInt(record.grade))
            .average().orElse(0);
    }

    private void checkSemester(int semester) {
        if (semester < 1 || semester > 13) {
            throw new IndexOutOfBoundsException("Semester out of bounds");
        }
    }

    private static Grade mapIntToGrade(int grade) {
        switch (grade) {
            case 2:
                return Grade.POOR;
            case 3:
                return Grade.SATISFACTORY;
            case 4:
                return Grade.GOOD;
            case 5:
                return Grade.EXCELLENT;
            default:
                throw new IndexOutOfBoundsException("Grade out of bounds");
        }
    }

    private static int mapGradeToInt(Grade grade) {
        switch (grade) {
            case POOR:
                return 2;
            case SATISFACTORY:
                return 3;
            case GOOD:
                return 4;
            case EXCELLENT:
                return 5;
            default:
                throw new IndexOutOfBoundsException("Grade out of bounds");
        }
    }
}
