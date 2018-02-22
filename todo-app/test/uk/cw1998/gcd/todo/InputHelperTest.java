package uk.cw1998.gcd.todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.cw1998.gcd.todo.util.InputHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;

class InputHelperTest {

    private String[] options = new String[]{"0", "1", "2", "3"};

    @Test
    void buildMenu_happyPath_returnsCorrectNumber() {
        String consoleInput = "1\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals(1, inputHelper.buildMenu("Title", options));
    }

    @Test
    void buildMenu_optionOutsideRange_allowsASecondChance() {
        String consoleInput = "5\r\n1\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals(1, inputHelper.buildMenu("Title", options));
    }

    @Test
    void buildMenu_oneOptionInList_returnsOneWithoutInput() {
        String consoleInput = "";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals(1, inputHelper.buildMenu("Title", new String[]{""}));
    }

    @Test
    void buildMenu_lettersProvided_allowsASecondChance() {
        String consoleInput = "hello\r\n1\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals(1, inputHelper.buildMenu("Title", options));
    }

    @Test
    void getString_notRequired_returnsCorrectInput() {
        String consoleInput = "Hello world!\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals("Hello world!", inputHelper.getString("Test Hello world!"));
    }

    @Test
    void getString_notRequired_noInput_returnsEmptyString() {
        String consoleInput = "\r\n\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals("", inputHelper.getString("Test blank"));
    }

    @Test
    void getString_required_returnsCorrectInput() {
        String consoleInput = "Hello world!\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals("Hello world!", inputHelper.getString("Test required", true));
    }

    @Test
    void getString_required_noInput_allowsASecondChance() {
        String consoleInput = "\r\nSecond Chances\r\n";
        InputHelper inputHelper = new InputHelper(new Scanner(consoleInput));

        Assertions.assertEquals("Second Chances", inputHelper.getString("", true));
    }

    @Test
    void inputDate_validDateForwardSlashes_returnsDateObject() {
        String dateInput = "22/02/2018";
        LocalDate expected = LocalDate.of(2018, 2, 22);

        Assertions.assertEquals(expected, InputHelper.inputDate(dateInput));
    }

    @Test
    void inputDate_validDateHyphens_returnsDateObject() {
        String dateInput = "22-02-2018";
        LocalDate expected = LocalDate.of(2018, 2, 22);

        Assertions.assertEquals(expected, InputHelper.inputDate(dateInput));
    }

    @Test
    void inputDate_invalidDate_returnsDateObject() {
        String dateInput = "40/02/2018";

        Assertions.assertNull(InputHelper.inputDate(dateInput));
    }

    @Test
    void inputDate_emptyString_returnsNull() {
        String dateInput = "";

        Assertions.assertNull(InputHelper.inputDate(dateInput));
    }

    @Test
    void inputDate_nullString_returnsNull() {
        String dateInput = null;

        Assertions.assertNull(InputHelper.inputDate(dateInput));
    }
}
