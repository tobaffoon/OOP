package ru.nsu.amazyar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.RecordBook.AssessmentForm;

class RecordBookTest {

    RecordBook recordBook;

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

    @Test
    public void averageScoreTest() {
        //according to cab.nsu.ru it equals 4.7,
        //but we can't set precision of double, so we check this wy
        Assertions.assertTrue(
            4.7 < recordBook.getAverageScore() && recordBook.getAverageScore() < 4.8);
    }

}