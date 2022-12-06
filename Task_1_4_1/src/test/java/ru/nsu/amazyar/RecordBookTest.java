package ru.nsu.amazyar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.RecordBook.AssessmentForm;

class RecordBookTest {

    RecordBook recordBook;

    /**
     * Create test recordBook and fill it with data.
     */
    @BeforeEach
    public void initTest() {
        recordBook = new RecordBook();

        recordBook.addRecord("Calculus", 1,
            "Vaskevich Vladimir Leontevich", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Discrete Math", 1,
            "Vlasov Dmitri Yurievich", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Declarative Programming", 1,
            "Vlasov Vladimir Nikolaevich", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Imperative Programming", 1,
            "Nesterenko Tatyana Viktorovna", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("English language", 1,
            "Khotskina Olga Valerevna", true, AssessmentForm.CREDIT);
        recordBook.addRecord("History", 1,
            "Oplakanskaya Renata Valerevna", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Fundamentals of speech culture", 1,
            "Zavorina Tatyana Ivanovna", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Physical education", 1,
            "Oparin Grigoriy Andreevich", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Digital platforms", 1,
            "Irtegov Dmitriy Valentinovich", true, AssessmentForm.CREDIT);

        recordBook.addRecord("Calculus", 2,
            "Vaskevich Vladimir Leontevich", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Graph Theory", 2,
            "Apanovich Zinaida Vladimirovna", 5, AssessmentForm.EXAM);
        recordBook.addRecord("SQL", 2,
            "Miginskiy Denis Sergeevich", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Measuring practice", 2,
            "Belousov Roman Gennadevich", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Imperative Programming", 2,
            "Nesterenko Tatyana Viktorovna", 5, AssessmentForm.EXAM);
        recordBook.addRecord("English language", 2,
            "Khotskina Olga Valerevna", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Physical education", 2,
            "Oparin Grigoriy Andreevich", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Digital platforms", 2,
            "Irtegov Dmitriy Valentinovich", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
    }

    /**
     * Test for the most common cases.
     */
    @Test
    public void regularTest() {
        //according to cab.nsu.ru it equals 4.7
        double averageScore =
            BigDecimal.valueOf(recordBook.getAverageScore()).setScale(1, RoundingMode.CEILING)
                .doubleValue();
        Assertions.assertEquals(4.7, averageScore);

        Assertions.assertFalse(recordBook.hasBadMarks());
        Assertions.assertFalse(recordBook.getsHonourDegree());
        Assertions.assertFalse(recordBook.getsIncreasedScholarship());

        Assertions.assertThrows(IllegalStateException.class,
            () -> recordBook.getQualificationWorkGrade());
        recordBook.setQualificationWorkGrade(5);
        Assertions.assertEquals(5, recordBook.getQualificationWorkGrade());

        recordBook.addRecord("Some discipline", 1, "Him", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Another discipline", 2, "Her", 5, AssessmentForm.EXAM);
        Assertions.assertTrue(recordBook.getsHonourDegree());

        recordBook.setQualificationWorkGrade(3);
        Assertions.assertEquals(3, recordBook.getQualificationWorkGrade());

        Assertions.assertFalse(recordBook.getsHonourDegree());

        recordBook.addRecord("Digital platforms", 3, "Not Irtegov Dmitriy Valentinovich", false,
            AssessmentForm.CREDIT);
        recordBook.addRecord("Philosophy", 4, "Plato", 2, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("War art", 4, "Sun Tzu", 3, AssessmentForm.EXAM);
        Assertions.assertTrue(recordBook.hasBadMarks());
        Assertions.assertFalse(recordBook.getsHonourDegree());

        averageScore =
            BigDecimal.valueOf(recordBook.getAverageScore()).setScale(1, RoundingMode.CEILING)
                .doubleValue();
        Assertions.assertEquals(4.5, averageScore);
    }

    /**
     * Test program's behaviour with IndexOutOfBoundsException.
     */
    @Test
    public void outOfBoundsTest() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> recordBook.addRecord("Name", -1, "Somebody", true, AssessmentForm.CREDIT));
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> recordBook.addRecord("Name", 1, "Someone", 6, AssessmentForm.EXAM));
    }

    /**
     * Test program's behaviour with IllegalStateException.
     */
    @Test
    public void illegalStateTest() {
        Assertions.assertThrows(IllegalStateException.class,
            () -> recordBook.addRecord("Name", 1, "Somebody", true,
                AssessmentForm.DIFFERENTIAL_CREDIT));
        Assertions.assertThrows(IllegalStateException.class,
            () -> recordBook.addRecord("Name", 1, "Someone", 3, AssessmentForm.CREDIT));
        Assertions.assertThrows(IllegalStateException.class,
            () -> recordBook.getQualificationWorkGrade());
    }

    /**
     * Test program for not trivial behaviour concerning scholarship.
     */
    @Test
    public void scholarshipTest() {
        recordBook = new RecordBook();
        recordBook.addRecord("Name1", 1, "Somebody", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Name2", 1, "Someone", 5, AssessmentForm.DIFFERENTIAL_CREDIT);

        Assertions.assertFalse(recordBook.getsIncreasedScholarship());

        recordBook.addRecord("Name1", 2, "Somebody", 3, AssessmentForm.EXAM);
        Assertions.assertTrue(recordBook.getsIncreasedScholarship());
    }

    @Test
    public void nullTest(){
        Assertions.assertThrows(NullPointerException.class, () -> recordBook.addRecord(null,1,"null",2,AssessmentForm.EXAM));
        Assertions.assertThrows(NullPointerException.class, () -> recordBook.addRecord("null",1,null,2,AssessmentForm.EXAM));
    }
}