package co.com.jorge.model;

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
    private final int rows;

    /**
     * Número de columnas
     */
    private final int columns;

    /**
     * Contador de clics efectuados sobre el tablero
     */
    private int counterClick;

    /**
     * Lista de objetos de la clase Sheet
     */
    private static List<Sheet> sheetList;

    /**
     * Lista de números parejas según el producto de filas y columnas
     */
    private final List<String> coupleNumbers;

    /**
     * generador de números aleatorios según el producto de filas y columnas, para asignar posiciones aleatorias
     * a las imágenes cada vez que se crea un tablero nuevo
     */
    private final Random randomGenerator;

    /**
     * Número de cuadrículas del tablero
     */
    private int boxes;

    /**
     * Constructor de la clase Table, obtiene número de filas y columnas, determina el número de cuadrículas,
     * instancia el generador aleatorio de la clase Random, Instancia la lista de parejas de números para ser asignados
     * a cada objeto de la clase Sheet
     * @param level enumeración que determina número de filas y columnas
     */
    public Table(Level level) {
        this.rows = level.getRows();
        this.columns = level.getColumns();
        this.boxes = rows * columns;
        this.randomGenerator = new Random();
        this.coupleNumbers = new ArrayList<>();
        prepararRandom();
        loadSheets();
    }

    /**
     * Crea tantos objetos de la clase Sheet como columnas y filas haya, y los adiciona al atributo sheetList,
     * Carga las imágenes en cada objeto de la clase Sheet
     */
    public void loadSheets() {
        sheetList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int type = randomNumero();
                Sheet sheet = new Sheet(type);
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
     * Método que retorna el número de filas del tablero
     * @return Devuelve el número de filas del tablero
     */
    public int getRows() {
        return rows;
    }

    /**
     * Método que retorna un número comprendido en el rango del producto de filas y columnas, contenido en lista
     * "coupleNumbers" y elimina este elemento de la lista.
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
     * Método que retorna el número de columnas del tablero
     * @return Retorna el número de columnas del tablero
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Método que retorna una lista con todos los objetos construidos de la clase Sheet
     * @return retorna una lista con todos los objetos construidos de la clase Sheet
     */
    public List<Sheet> getSheetList() {
        return sheetList;
    }

    /**
     * Método que permite determinar si todos los objetos de la clase Sheet han sido descubiertos, por lo tanto,
     * marcados como pares
     * @return retorna true si todas las fichas han sido marcadas como pares de lo contrario retorna false
     */
    public boolean isAllSheetsPairs(){
        return sheetList.stream().allMatch(Sheet::isPair);
    }

    /**
     * Método que retorna contador de clics efectuados sobre el tablero
     * @return Retorna contador de clics efectuados sobre el tablero
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
