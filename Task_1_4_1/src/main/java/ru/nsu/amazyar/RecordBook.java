package ru.nsu.amazyar;

public class RecordBook {

    private abstract class Discipline{
        public final String name;

        public Discipline(String name) {
            this.name = name;
        }
    }

    private class CreditDiscipline extends Discipline {
        public final boolean pass;

        public CreditDiscipline(String name, boolean pass) {
            super(name);
            this.pass = pass;
        }
    }

    private class DifferentialCreditDiscipline extends Discipline{
        private final int grade;
        private int GRADE_LOW_BOUND = 1;
        private int GRADE_HIGH_BOUND = 5;

        public DifferentialCreditDiscipline(String name, int grade) {
            super(name);
            this.grade = grade;
        }

        public DifferentialCreditDiscipline(String name, int grade,
            int GRADE_LOW_BOUND, int GRADE_HIGH_BOUND) {
            super(name);
            this.grade = grade;
            this.GRADE_LOW_BOUND = GRADE_LOW_BOUND;
            this.GRADE_HIGH_BOUND = GRADE_HIGH_BOUND;
        }
    }
}
