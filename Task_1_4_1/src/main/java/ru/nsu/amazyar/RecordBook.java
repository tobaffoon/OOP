package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;

public class RecordBook {
    private List<Discipline> records;
    private int qualificationWorkGrade;
    private enum Grade {
        FAIL, PASS, POOR, SATISFACTORY, GOOD, EXCELLENT
    }

    private abstract class Discipline{
        public final String name;
        private final List<Grade> results;
        public Discipline(String name) {
            results = new ArrayList<>();
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

        public DifferentialCreditDiscipline(String name, int grade) {
            super(name);
            this.grade = grade;
        }
    }
}
