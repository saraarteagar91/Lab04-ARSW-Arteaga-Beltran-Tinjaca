package edu.eci.arsw.collabboard.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardElementTest {

    @Test
    void shouldRejectNullOrBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> new BoardElement(null, ElementType.RECTANGLE, 0, 0, 1, 1, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new BoardElement(" ", ElementType.RECTANGLE, 0, 0, 1, 1, ""));
    }

    @Test
    void shouldRejectNullType() {
        assertThrows(IllegalArgumentException.class,
                () -> new BoardElement("el-1", null, 0, 0, 1, 1, ""));
    }

    @Test
    void shouldRejectNegativeWidthOrHeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new BoardElement("el-1", ElementType.RECTANGLE, 0, 0, -1, 1, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new BoardElement("el-1", ElementType.RECTANGLE, 0, 0, 1, -1, ""));
    }

    @Test
    void shouldDefaultNullTextToEmptyString() {
        BoardElement element = new BoardElement("el-1", ElementType.TEXT, 0, 0, 1, 1, null);

        assertEquals("", element.text());
    }

    @Test
    void shouldAcceptZeroWidthAndHeight() {
        BoardElement element = new BoardElement("el-1", ElementType.RECTANGLE, 0, 0, 0, 0, "");

        assertEquals(0, element.width());
        assertEquals(0, element.height());
    }
}
