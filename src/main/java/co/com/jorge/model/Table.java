package co.com.jorge.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {

    private int rows;

    private int columns;

    private int counterClick;

    private static List<Sheet> sheetList = new ArrayList<>();

    private List<String> number = new ArrayList<>();

    private Random randomGenerator = new Random();

    private int boxes;

    public Table(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.boxes = rows * columns;
        prepararRandom();
        loadSheets();
    }

    public void loadSheets() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int type = randomNumero();
                ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/img/img" + type + ".jpg")));
                view.setId("img"+ type);
                view.setFitHeight(125);
                view.setFitWidth(125);
                Sheet sheet = new Sheet(view);
                sheetList.add(sheet);
            }
        }
    }

    // Llena la lista number con parejas de números según la mitad de la cantidad de filas por columnas
    public void prepararRandom() {
        for (int i = 0; i < (rows * columns / 2); i++) {
            number.add(String.valueOf(i + 1));
            number.add(String.valueOf(i + 1));
        }
    }

    public int getRows() {
        return rows;
    }

    // Retorna un número comprendido entre la mitad de la cantidad de filas por columnas contenido en lista "number"
    // y elimina este elemento de la lista.
    public int randomNumero() {
        int retorno, random;
        random = randomGenerator.nextInt(boxes);
        retorno = Integer.parseInt(number.get(random));
        number.remove(random);
        boxes -= 1;
        return retorno;
    }

    public static void coverAllImage() {
        sheetList.forEach(sheet -> {
            sheet.setCover(true);
            sheet.setPair(false);
        });
    }

    public static void coverAllImageNoPair(){
        sheetList.forEach(sheet -> {
            if (!sheet.isPair() && !sheet.isCover()) {
                sheet.coverImage();
            }
        });
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Sheet> getSheetList() {
        return sheetList;
    }

    public boolean isAllSheetsPairs(){
        if (sheetList.stream().allMatch(Sheet::isPair))
        {
            return true;
        }
        return false;
    }

    public void setSheetList(List<Sheet> sheetList) {
        this.sheetList = sheetList;
    }

    public int getCounterClick() {
        return counterClick;
    }

    public void setCounterClick(int counterClick) {
        this.counterClick = counterClick;
    }


}
