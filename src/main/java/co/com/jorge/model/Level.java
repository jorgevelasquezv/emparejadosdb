package co.com.jorge.model;

/**
 * Representación de la lista de constantes que representan los niveles preestablecidos del juego
 */

public enum Level {

    /**
     * Niveles predefinidos por filas y columnas
     */
    LEVEL1(4,3), LEVEL2(4,4), LEVEL3(5,4);

    /**
     * Número de filas
     */
    private final int rows;

    /**
     * Número de columnas
     */
    private final int columns;

    /**
     * Constructor de la enumeración
     * @param rows Número de filas
     * @param columns Número de columnas
     */
    Level(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * @return Retorna el número de filas
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return Retorna el número de columnas
     */
    public int getColumns() {
        return columns;
    }
}
