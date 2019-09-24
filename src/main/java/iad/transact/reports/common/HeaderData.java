package iad.transact.reports.common;

import java.awt.Image;

public class HeaderData {

  private String headerText;
  private Image headerImage;

  private HeaderData(Builder builder) {
    this.headerText = builder.headerText;
    this.headerImage = builder.headerImage;
  }

  public static class Builder {
    private String headerText;
    private Image headerImage;

    public Builder setHeaderText(String headerText) {
      this.headerText = headerText;
      return this;
    }

    public Builder setHeaderImage(Image headerImage) {
      this.headerImage = headerImage;
      return this;
    }

    public HeaderData build() {
      return new HeaderData(this);
    }
  }

  public String getHeaderText() {
    return headerText;
  }

  public Image getHeaderImage() {
    return headerImage;
  }
}
