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
    }

    @Test
    public void averageScoreTest() {
        recordBook.addRecord("Введение в алгебру и анализ", 1,
            "Васкевич Владимир Леонтьевич", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Введение в дискретную математику и математическую логику", 1,
            "Власов Дмитрий Юрьевич", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Декларативное программирование", 1,
            "Власов Владимир Николаевич", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Императивное программирование", 1,
            "Нестеренко Татьяна Викторовна", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Иностранный язык", 1,
            "Хоцкина Ольга Валерьевна", true, AssessmentForm.CREDIT);
        recordBook.addRecord("История", 1,
            "Оплаканская Рената Валерьевна", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Основы культуры речи", 1,
            "Заворина Татьяна Ивановна", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Физическая культура и спорт", 1,
            "Опарин Григорий Андреевич", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Физическая культура и спорт (элективная дисциплина)",
            1, "Опарин Григорий Андреевич", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Цифровые платформы", 1,
            "Иртегов Дмитрий Валентинович", true, AssessmentForm.CREDIT);

        recordBook.addRecord("Введение в алгебру и анализ", 2,
            "Васкевич Владимир Леонтьевич", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Введение в дискретную математику и математическую логику", 2,
            "Апанович Зинаида Владимировна", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Декларативное программирование", 2,
            "Власов Владимир Николаевич", 5, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Измерительный практикум", 2,
            "Белоусов Роман Геннадьевич", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Императивное программирование", 2,
            "Нестеренко Татьяна Викторовна", 5, AssessmentForm.EXAM);
        recordBook.addRecord("Иностранный язык", 2,
            "Хоцкина Ольга Валерьевна", 4, AssessmentForm.DIFFERENTIAL_CREDIT);
        recordBook.addRecord("Физическая культура и спорт", 2,
            "Опарин Григорий Андреевич", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Физическая культура и спорт (элективная дисциплина)",
            2, "Опарин Григорий Андреевич", true, AssessmentForm.CREDIT);
        recordBook.addRecord("Цифровые платформы", 2,
            "Иртегов Дмитрий Валентинович", 4, AssessmentForm.DIFFERENTIAL_CREDIT);

        //according to cab.nsu.ru it equals 4.7,
        //but we can't set precision of double, so we check this wy
        Assertions.assertTrue(
            4.7 < recordBook.getAverageScore() && recordBook.getAverageScore() < 4.8);
    }

}