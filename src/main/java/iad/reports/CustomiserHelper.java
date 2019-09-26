package iad.reports;

import iad.transact.reports.corporateactionelection.ReportElement;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class CustomiserHelper {

  private CustomiserHelper() {}

  // done to avoid repeated casting
  public static JRDesignElement getDesignElementByKey(JRElementGroup band, String key) {
    return (JRDesignElement) band.getElementByKey(key);
  }

  public static void hideElementByKey(JRBand band, String key) {
    JRDesignElement designElement = getDesignElementByKey(band, key);
    designElement.setPrintWhenExpression(new JRDesignExpression());
  }

  // iterates over the keys to build report elements
  public static Map<String, ReportElement> getBandElementsByKeys(
      JRElementGroup band, Iterable<String> keys) {
    Map<String, ReportElement> reportElements = new HashMap<>();
    keys.forEach(
        key -> {
          JRElement bandElement = band.getElementByKey(key);
          if (bandElement == null) {
            throw new IllegalArgumentException(
                String.format("The requested key (%s) does not exists within the report.", key));
          }
          ReportElement element =
              new ReportElement(
                  bandElement.getKey(),
                  bandElement.getX(),
                  bandElement.getY(),
                  bandElement.getWidth(),
                  bandElement.getHeight());
          reportElements.put(bandElement.getKey(), element);
        });
    return reportElements;
  }
}
