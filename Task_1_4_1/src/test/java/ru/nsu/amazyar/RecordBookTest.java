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
    public void funnyTest() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> recordBook.addRecord  (
                "discipline", "teacher", 6, AssessmentForm.EXAM
                                        )
        );
    }

}