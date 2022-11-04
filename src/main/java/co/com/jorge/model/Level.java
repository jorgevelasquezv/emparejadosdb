package co.com.jorge.model;

public enum Level {

    LEVEL1(4,3), LEVEL2(4,4), LEVEL3(5,4);

    private int rows;

    private int columns;

    Level(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
