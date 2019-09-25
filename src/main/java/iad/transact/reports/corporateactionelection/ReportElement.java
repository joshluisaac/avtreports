package iad.transact.reports.corporateactionelection;

public class ReportElement implements Comparable<ReportElement> {

  private int xAxis;
  private int yAxis;
  private int width;
  private int height;
  private String key;

  public ReportElement(String key, int xAxis, int yAxis, int width, int height) {
    this.key = key;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
    this.width = width;
    this.height = height;
  }

  public int getxAxis() {
    return xAxis;
  }

  public void setxAxis(int xAxis) {
    this.xAxis = xAxis;
  }

  public int getyAxis() {
    return yAxis;
  }

  public void setyAxis(int yAxis) {
    this.yAxis = yAxis;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  // remove this
  @Override
  public String toString() {
    return "ReportElement{"
        + "xAxis="
        + xAxis
        + ", yAxis="
        + yAxis
        + ", width="
        + width
        + ", height="
        + height
        + ", key='"
        + key
        + '\''
        + '}';
  }

  @Override
  public int compareTo(ReportElement o) {
    return 0;
  }
}
