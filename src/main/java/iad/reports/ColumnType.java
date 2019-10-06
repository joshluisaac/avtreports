package iad.reports;

public enum ColumnType {
  HEADER("H", "Header"),
  DETAIL("FD", "FieldDetail");

  private final String type;
  private final String code;

  ColumnType(String type, String code) {
    this.type = type;
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public String getCode() {
    return code;
  }
}
