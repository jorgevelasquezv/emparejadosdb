package co.com.jorge.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase Table, representa el tablero con número de filas y columnas establecidas al realizar una instancia de la clase,
 * en el cual se alojan los objetos de la clase sheet, los cuales almacenan las imágenes
 */

public class Table {

    /**
     * Número de filas
     */
    private int rows;

    /**
     * Número de columnas
     */
    private int columns;

    /**
     * Contador de clics efectuados sobre el tablero
     */
    private int counterClick;

    /**
     * Lista de objetos de la clase Sheet
     */
    private static List<Sheet> sheetList = new ArrayList<>();

    /**
     * Lista de números parejas según el producto de filas y columnas
     */
    private List<String> coupleNumbers = new ArrayList<>();

    /**
     * generador de números aleatorios según el producto de filas y columnas, para asignar posiciones aleatorias
     * a las imágenes cada vez que se crea un tablero nuevo
     */
    private Random randomGenerator = new Random();

    /**
     * Número de cuadrículas del tablero
     */
    private int boxes;

    /**
     * Constructor de la clase Table
     * @param rows número de filas del tablero
     * @param columns número de columnas del tablero
     */
    public Table(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.boxes = rows * columns;
        prepararRandom();
        loadSheets();
    }

    /**
     * Crea tantos objetos de la clase Sheet como columnas y filas haya, y los adiciona al atributo sheetList,
     * Carga las imágenes en cada objeto de la clase Sheet
     */
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

    /**
     * Llena la lista coupleNumbers con parejas de números según la mitad del producto de filas y columnas
     */
    public void prepararRandom() {
        for (int i = 0; i < (rows * columns / 2); i++) {
            coupleNumbers.add(String.valueOf(i + 1));
            coupleNumbers.add(String.valueOf(i + 1));
        }
    }

    /**
     * @return Devuelve el número de filas del tablero
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return Retorna un número comprendido en el rango del producto de filas y columnas, contenido en lista
     * "coupleNumbers" y elimina este elemento de la lista.
     */
    public int randomNumero() {
        int retorno, random;
        random = randomGenerator.nextInt(boxes);
        retorno = Integer.parseInt(coupleNumbers.get(random));
        coupleNumbers.remove(random);
        boxes -= 1;
        return retorno;
    }

    /**
     * Cubre todas las imágenes de las fichas con una imagen en blanco
     */
    public static void coverAllImage() {
        sheetList.forEach(sheet -> {
            sheet.setCover(true);
            sheet.setPair(false);
        });
    }

    /**
     * Cubre las imágenes de las fichas que no han sido marcadas como par
     */
    public static void coverAllImageNoPair(){
        sheetList.forEach(sheet -> {
            if (!sheet.isPair() && !sheet.isCover()) {
                sheet.coverImage();
            }
        });
    }

    /**
     * Modifica el número de filas del tablero
     * @param rows número de filas del tablero
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @return retorna el número de columnas del tablero
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Modifica el número de columnas del tablero
     * @param columns número de columnas del tablero
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * @return retorna una lista con todos los objetos construidos de la clase Sheet
     */
    public List<Sheet> getSheetList() {
        return sheetList;
    }

    /**
     * @return retorna true si todas las fichas han sido marcadas como pares de lo contrario retorna false
     */
    public boolean isAllSheetsPairs(){
        return sheetList.stream().allMatch(Sheet::isPair);
    }

    /**
     * Modifica la lista de objetos de la clase Sheet
     * @param sheetList lista de objetos creados de la clase Sheet
     */
    public void setSheetList(List<Sheet> sheetList) {
        this.sheetList = sheetList;
    }

    /**
     * @return contador de clics efectuados sobre el tablero
     */
    public int getCounterClick() {
        return counterClick;
    }

    /**
     * Modifica el contador de clics efectuados sobre el tablero
     * @param counterClick contador de clics efectuados sobre el tablero
     */
    public void setCounterClick(int counterClick) {
        this.counterClick = counterClick;
    }


}
