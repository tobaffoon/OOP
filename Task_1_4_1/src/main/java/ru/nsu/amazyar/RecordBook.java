package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecordBook {

    private final Map<String, List<Record>> records;
    private int qualificationWorkGrade;
    private boolean qualificationWorkDone;

    private final static int MAX_SEMESTER = 13;

    private enum Grade {
        FAIL, PASS, POOR, SATISFACTORY, GOOD, EXCELLENT
    }

    /**
     * Possible forms of certification
     */
    public enum AssessmentForm {
        CREDIT, DIFFERENTIAL_CREDIT, EXAM
    }

    /**
     * Regular constructor
     */
    public RecordBook() {
        this.records = new HashMap<>();
        this.qualificationWorkGrade = -1;
        this.qualificationWorkDone = false;
    }

    private record Record(String teacher, int semester, Grade grade, AssessmentForm form) {

    }

    /**
     * Add new record to the book. Accounts for disciplines which can only be passed or failed
     *
     * @param discipline name of a subject
     * @param semester   semester number
     * @param teacher    teacher's name
     * @param pass       true if subject was passed, false otherwise
     * @param form       form of certification (must be CREDIT)
     * @throws IllegalStateException if form isn't credit
     */
    public void addRecord(String discipline, int semester, String teacher, boolean pass,
        AssessmentForm form) throws IllegalStateException {
        checkSemester(semester);
        if (form != AssessmentForm.CREDIT) {
            throw new IllegalStateException("Inappropriate value for assessment form");
        }
        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        if (pass) {
            previousRecords.add(new Record(teacher, semester, Grade.PASS, AssessmentForm.CREDIT));
        } else {
            previousRecords.add(new Record(teacher, semester, Grade.FAIL, AssessmentForm.CREDIT));
        }
        records.putIfAbsent(discipline, previousRecords);
    }

    /**
     * Add new record to the book. Accounts for disciplines which are assessed via mark
     *
     * @param discipline name of a subject
     * @param semester   semester number
     * @param teacher    teacher's name
     * @param grade      grade for completing this course
     * @param form       form of certification (must be EXAM or DIFFERENTIAL CREDIT)
     * @throws IllegalStateException if form isn't EXAM or DIFFERENTIAL CREDIT
     */
    public void addRecord(String discipline, int semester, String teacher, int grade,
        AssessmentForm form)
        throws IndexOutOfBoundsException, IllegalStateException {
        checkSemester(semester);
        // Credited discipline must not have integer as the grade
        if (form != AssessmentForm.EXAM && form != AssessmentForm.DIFFERENTIAL_CREDIT) {
            throw new IllegalStateException("Inappropriate value for assessment form");
        }

        List<Record> previousRecords = records.getOrDefault(discipline, new ArrayList<>());
        previousRecords.add(new Record(teacher, semester, mapIntToGrade(grade), form));
        records.putIfAbsent(discipline, previousRecords);
    }

    /**
     * Gets qualification work grade.
     *
     * @throws IllegalStateException if qualification work grade wasn't set before
     */
    public int getQualificationWorkGrade() throws IllegalStateException {
        if (qualificationWorkDone) {
            return qualificationWorkGrade;
        }

        throw new IllegalStateException("Qualification work hasn't been assessed");
    }

    /**
     * Sets qualification work grade.
     */
    public void setQualificationWorkGrade(int qualificationWorkGrade) {
        this.qualificationWorkDone = true;
        this.qualificationWorkGrade = qualificationWorkGrade;
    }

    /**
     * Gets average of all marks in the book.
     */
    public double getAverageScore() {
        return records.values().stream()
            .flatMap(List::stream)
            .filter(record -> record.form == AssessmentForm.EXAM
                || record.form == AssessmentForm.DIFFERENTIAL_CREDIT)
            .mapToInt(record -> mapGradeToInt(record.grade))
            .average().orElse(0);
    }

    /**
     * Checks if book contains bad marks.
     */
    public boolean hasBadMarks() {
        return records.values().stream().flatMap(List::stream).anyMatch(
            record -> record.grade == Grade.FAIL || record.grade == Grade.POOR
                || record.grade == Grade.SATISFACTORY);
    }

    /**
     * Checks if one can get honour degree with this book.
     */
    public boolean getsHonourDegree() {
        List<Record> lastSemesterRecords = records.values().stream()
            .map(oneSubjectRecords -> oneSubjectRecords.stream() //get record of last (max) semester
                .max(Comparator.comparingInt(records -> records.semester)))
            .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        double lastExcellentMarks = (double) lastSemesterRecords.stream()
            .filter(record -> record.grade == Grade.EXCELLENT)
            .count();

        double markedDisciplines = (double) lastSemesterRecords.stream()
            .filter(record -> record.form == AssessmentForm.EXAM
                || record.form == AssessmentForm.DIFFERENTIAL_CREDIT)
            .count();

        return !hasBadMarks() &&
            (lastExcellentMarks / markedDisciplines >= 0.75) &&
            (qualificationWorkGrade == 5 || !qualificationWorkDone);
    }

    /**
     * Checks if one can get increased scholarship with this book.
     */
    public boolean getsIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        if (currentSemester == 1) {
            return false;
        }

        return records.values().stream().flatMap(List::stream)
            .filter(record -> record.semester == currentSemester - 1)
            .allMatch(record -> record.grade == Grade.EXCELLENT || record.grade == Grade.PASS);
    }

    private int getCurrentSemester() {
        return this.records.values().stream()
            .flatMap(oneSubjectRecords -> oneSubjectRecords.stream().map(Record::semester))
            .max(Comparator.naturalOrder()).orElse(1);
    }

    private void checkSemester(int semester) throws IndexOutOfBoundsException {
        if (semester < 1 || semester > MAX_SEMESTER) {
            throw new IndexOutOfBoundsException("Semester out of bounds");
        }
    }

    private static Grade mapIntToGrade(int grade) throws IndexOutOfBoundsException {
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

    private static int mapGradeToInt(Grade grade) throws IndexOutOfBoundsException {
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
