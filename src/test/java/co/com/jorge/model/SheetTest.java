package co.com.jorge.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SheetTest {

    Sheet sheet = new Sheet(1);

    @Test
    public void cover_image() {
        sheet.coverImage();
        assertTrue(sheet.isCover());
    }

    @Test
    public void uncover_image() {
        sheet.uncoverImage();
        assertFalse(sheet.isCover());
    }

    @Test
    public void two_sheet_are_equals() {
        Sheet sheetTwo = new Sheet(1);
        assertTrue(sheet.equals(sheetTwo));
    }

    @Test
    public void two_sheet_are_not_equals() {
        Sheet sheetTwo = new Sheet(2);
        assertFalse(sheet.equals(sheetTwo));
    }

    @Test
    public void sheet_is_pair() {
        sheet.setPair(true);
        assertTrue(sheet.isPair());
    }

    @Test
    public void sheet_not_is_pair() {
        sheet.setPair(false);
        assertFalse(sheet.isPair());
    }
}