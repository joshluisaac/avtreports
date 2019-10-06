package iad.reports;

public class ColumnHeaderFieldPair {

  private String columnKey;
  private String fieldKey;
  private Boolean excluded;

  public ColumnHeaderFieldPair(String columnKey, String fieldKey, Boolean excluded) {
    this.columnKey = columnKey;
    this.fieldKey = fieldKey;
    this.excluded = excluded;
  }

  public String getColumnKey() {
    return columnKey;
  }

  public String getFieldKey() {
    return fieldKey;
  }

  public Boolean isExcluded() {
    return excluded;
  }
}
