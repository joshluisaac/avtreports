package iad.transact.reports.common;

import lombok.Builder;
import lombok.Value;

import java.awt.Image;

@Builder @Value
public class HeaderData {

  private String headerText;
  private Image headerImage;


}
