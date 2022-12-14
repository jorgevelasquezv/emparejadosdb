package co.com.jorge.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static co.com.jorge.model.Level.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableTest {

    @Test
    public void check_number_of_sheet_for_level_one() {
        Table table = new Table(LEVEL1);
        assertEquals(12, table.getSheetList().size());
    }

    @Test
    public void check_number_of_sheet_for_level_two() {
        Table table = new Table(LEVEL2);
        assertEquals(16, table.getSheetList().size());
    }

    @Test
    public void check_number_of_sheet_for_level_three() {
        Table table = new Table(LEVEL3);
        assertEquals(20, table.getSheetList().size());
    }

    @Test
    public void all_images_of_sheets_is_cover_in_start_play() {
        Table table = new Table(LEVEL1);
        assertTrue(table.getSheetList().stream().allMatch(Sheet::isCover));
    }

    @Test
    public void list_of_couple_numbers_is_empty_in_start_play() {
        Table table = new Table(LEVEL1);
        assertTrue(table.getCoupleNumbers().isEmpty());
    }

    @Test
    public void list_of_couple_numbers_is_good_for_level_one() {
        List<Integer> compareTo = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6) ;
        Table table = new Table(LEVEL1);
        table.readyRandom();
        assertTrue(table.getCoupleNumbers().containsAll(compareTo));
    }

    @Test
    public void list_of_couple_numbers_is_good_for_level_two() {
        List<Integer> compareTo = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8) ;
        Table table = new Table(LEVEL2);
        table.readyRandom();
        assertTrue(table.getCoupleNumbers().containsAll(compareTo));
    }

    @Test
    public void list_of_couple_numbers_is_good_for_level_three() {
        List<Integer> compareTo = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10) ;
        Table table = new Table(LEVEL3);
        table.readyRandom();
        assertTrue(table.getCoupleNumbers().containsAll(compareTo));
    }

    @Test
    public void all_images_not_is_pair_of_sheet_is_cover() {
        Table table = new Table(LEVEL1);
        table.getSheetList().forEach(sheet -> {
            if (sheet.getImage().getId().equals("img2")) {
                sheet.setPair(true);
            }
        });
        assertTrue(table.getSheetList().stream().allMatch(Sheet::isCover));
    }
}