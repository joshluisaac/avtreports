package iad.reports;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class CustomiserHelper {

  private CustomiserHelper() {}

  public static List<JRElement> getBandElementsByKeys(JRElementGroup band, Iterable<String> keys) {
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

  public static void resizeElements(List<JRElement> bandElements, double rebalanceFactor) {
    AtomicInteger totalWidth = new AtomicInteger(0);
    IntStream.range(0, bandElements.size())
        .forEach(
            index -> {
              JRElement column = bandElements.get(index);
              double newWidth = (column.getWidth() * rebalanceFactor);
              if (index == 0) {
                column.setWidth((int) Math.round(newWidth));
              } else {
                column.setX(totalWidth.get());
                column.setWidth((int) Math.round(newWidth));
              }
              totalWidth.set(totalWidth.get() + (int) Math.round(newWidth));
            });
  }

  public static void hideElements(Iterable<String> keys, JRBand band) {
    keys.forEach(entry -> CustomiserHelper.hideElementByKey(band, entry));
  }

  public static List<JRElement> remove(Collection<String> keys, List<JRElement> bandElements) {
    List<JRElement> elements = new ArrayList<>(bandElements);
    elements.removeIf(element -> keys.contains(element.getKey()));
    return elements;
  }

  private static void rebalance(JRBand band, List<String> excludeKeys, List<String> elementKeys) {
    List<JRElement> initialBandElements = CustomiserHelper.getBandElementsByKeys(band, elementKeys);
    int initialTableWidth = initialBandElements.stream().mapToInt(JRElement::getWidth).sum();
    List<JRElement> finalBandElements = CustomiserHelper.remove(excludeKeys, initialBandElements);
    int finalTableWidth = finalBandElements.stream().mapToInt(JRElement::getWidth).sum();
    // Precision fear: Not sure about this double though,int should work.
    double rebalanceFactor = (double) initialTableWidth / finalTableWidth;
    CustomiserHelper.hideElements(excludeKeys, band);
    CustomiserHelper.resizeElements(finalBandElements, rebalanceFactor);
  }

  public static void rebalance(
      JRBand band,
      Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs,
      ColumnType columnType) {
    if (columnType == null) {
      throw new IllegalArgumentException(
          "The column type is unspecified. \n Please ensure column type is set.");
    }
    List<String> elementKeys;
    List<String> excludedKeys;
    if (columnType.getType().equals("H")) {
      excludedKeys =
          columnHeaderFieldPairs.stream()
              .filter(ColumnHeaderFieldPair::isExcluded)
              .map(ColumnHeaderFieldPair::getColumnKey)
              .collect(Collectors.toList());
      elementKeys =
          columnHeaderFieldPairs.stream()
              .map(ColumnHeaderFieldPair::getColumnKey)
              .collect(Collectors.toList());
    } else if (columnType.getType().equals("FD")) {
      excludedKeys =
          columnHeaderFieldPairs.stream()
              .filter(ColumnHeaderFieldPair::isExcluded)
              .map(ColumnHeaderFieldPair::getFieldKey)
              .collect(Collectors.toList());
      elementKeys =
          columnHeaderFieldPairs.stream()
              .map(ColumnHeaderFieldPair::getFieldKey)
              .collect(Collectors.toList());
    } else {
      throw new IllegalArgumentException(
          String.format("The specified column type (%s) isn't supported.", columnType.getType()));
    }
    rebalance(band, excludedKeys, elementKeys);
  }

  public static JRDesignElement getDesignElementByKey(JRElementGroup band, String key) {
    return (JRDesignElement) band.getElementByKey(key);
  }

  public static void hideElementByKey(JRBand band, String key) {
    JRDesignElement designElement = getDesignElementByKey(band, key);
    designElement.setPrintWhenExpression(new JRDesignExpression());
  }
}
