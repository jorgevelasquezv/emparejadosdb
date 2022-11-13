package co.com.jorge.model;

/**
 * Representación de la lista de constantes que permite conocer los niveles preestablecidos del juego
 */

public enum Level {

    /**
     * Nivel 1 predefinido para 4 filas y 3 columnas
     */
    LEVEL1(4,3),

    /**
     * Nivel 2 predefinido para 4 filas y 4 columnas
     */
    LEVEL2(4,4),

    /**
     * Nivel 3 predefinido para 5 filas y 4 columnas
     */
    LEVEL3(5,4);

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
     * Método que permite obtener el valor del número de filas
     * @return Retorna el número de filas
     */
    public int getRows() {
        return rows;
    }

    /**
     * Método que permite obtener el valor del número de columnas
     * @return Retorna el número de columnas
     */
    public int getColumns() {
        return columns;
    }
}
