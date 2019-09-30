package iad.reports;

import iad.transact.reports.corporateactionelection.ReportElement;

import java.util.*;
import java.util.stream.IntStream;

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
  public static Map<String, ReportElement> getBandElementsByKeys2(
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


    // iterates over the keys to build report elements
    public static List<JRElement> getBandElementsByKeys(
            JRElementGroup band, Iterable<String> keys) {
        List<JRElement> reportElements = new ArrayList<>();
        keys.forEach(
                key -> {
                    JRElement bandElement = band.getElementByKey(key);
                    if (bandElement == null) {
                        throw new IllegalArgumentException(
                                String.format("The requested key (%s) does not exists within the report.", key));
                    }
                    reportElements.add(bandElement);
                });
        return reportElements;
    }

    public static void updateElement(List<JRElement> columnElements, double rebalanceFactor) {
        Integer[] totalWidth = { 0 };
        IntStream.range(0, columnElements.size())
                .forEach(
                        index -> {
                            JRElement column = columnElements.get(index);
                            double newWidth = (column.getWidth() * rebalanceFactor);
                            if (index == 0) {
                                column.setWidth((int) Math.round(newWidth));
                            } else {
                                column.setX(totalWidth[0]);
                                column.setWidth((int) Math.round(newWidth));
                            }
                            totalWidth[0] = totalWidth[0] + (int) Math.round(newWidth);
                        });
    }

    public static void hideElements(List<String> keys, JRBand band) {
      keys.forEach(entry -> CustomiserHelper.hideElementByKey(band, entry));
    }



}
