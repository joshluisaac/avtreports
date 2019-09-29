package iad.reports;

public class ColumnFieldPair {

    private String columnKey;
    private String fieldKey;


    public ColumnFieldPair(String columnKey, String fieldKey) {
        this.columnKey = columnKey;
        this.fieldKey = fieldKey;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public String getFieldKey() {
        return fieldKey;
    }
}
