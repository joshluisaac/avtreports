package iad.reports;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;

public class CustomiserHelper {

  private CustomiserHelper() {}

  public static List<JRElement> getBandElementsByKeys(JRElementGroup band, Collection<String> keys) {
    return keys.stream().map(key -> {
      JRElement bandElement = band.getElementByKey(key);
      if (bandElement == null) {
        throw new IllegalArgumentException(
                String.format("The requested key (%s) does not exists within the report.", key));
      }
      return bandElement;
    }).collect(Collectors.toList());
  }


  public static List<JRElement> getBandElementsByKeysOld(JRElementGroup band, Collection<String> keys) {
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

  public static void resizeElements(Integer xCoOrdStartsAt, List<JRElement> bandElements, double rebalanceFactor) {
    AtomicInteger totalWidth = new AtomicInteger(0);
    IntStream.range(0, bandElements.size())
        .forEach(
            index -> {
              JRElement column = bandElements.get(index);
              int newWidth =(int)  Math.round((column.getWidth() * rebalanceFactor));
              if (index == 0) {
                if (xCoOrdStartsAt != null){
                  column.setX(xCoOrdStartsAt);
                }
                column.setWidth(newWidth);
              } else {
                column.setX(totalWidth.get());
                column.setWidth(newWidth);
              }
              totalWidth.set(totalWidth.get() + newWidth);
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

  private static void rebalance(Integer xCoOrdStartsAt, JRBand band, List<String> excludeKeys, List<String> elementKeys) {
    List<JRElement> initialBandElements = CustomiserHelper.getBandElementsByKeys(band, elementKeys);
    int initialTableWidth = initialBandElements.stream().mapToInt(JRElement::getWidth).sum();
    List<JRElement> finalBandElements = CustomiserHelper.remove(excludeKeys, initialBandElements);
    int finalTableWidth = finalBandElements.stream().mapToInt(JRElement::getWidth).sum();
    // Precision fear: Not sure about this double though,int should work.
    double rebalanceFactor = (double) initialTableWidth / finalTableWidth;
    CustomiserHelper.hideElements(excludeKeys, band);
    CustomiserHelper.resizeElements(xCoOrdStartsAt,finalBandElements, rebalanceFactor);
  }

  public static void rebalance(Integer xCoOrdStartsAt,
      JRBand band,
      Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs,
      ColumnType columnType) {
    List<String> elementKeys = getElementKeysMap(columnHeaderFieldPairs,columnType).get("elementKeys");
    List<String> excludedKeys = getElementKeysMap(columnHeaderFieldPairs,columnType).get("excludedKeys");
    rebalance(xCoOrdStartsAt,band, excludedKeys, elementKeys);
  }

  public static void rebalance(
          JRBand band,
          Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs,
          ColumnType columnType) {
    List<String> elementKeys = getElementKeysMap(columnHeaderFieldPairs,columnType).get("elementKeys");
    List<String> excludedKeys = getElementKeysMap(columnHeaderFieldPairs,columnType).get("excludedKeys");
    rebalance(null,band, excludedKeys, elementKeys);
  }

  private static Map<String, List<String>> getElementKeysMap(Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs, ColumnType columnType){
    if (columnType == null) {
      throw new IllegalArgumentException(
              "The column type is unspecified. \n Please ensure column type is set.");
    }
    List<String> elementKeys;
    List<String> excludedKeys;
    if (columnType.getType().equals("H")) {
      excludedKeys = getExcludedKeys(columnHeaderFieldPairs, ColumnHeaderFieldPair::getColumnKey);
      elementKeys = getElementKeys(columnHeaderFieldPairs,ColumnHeaderFieldPair::getColumnKey);
    } else if (columnType.getType().equals("FD")) {
      excludedKeys = getExcludedKeys(columnHeaderFieldPairs, ColumnHeaderFieldPair::getFieldKey);
      elementKeys = getElementKeys(columnHeaderFieldPairs,ColumnHeaderFieldPair::getFieldKey);
    } else {
      throw new IllegalArgumentException(
              String.format("The specified column type (%s) isn't supported.", columnType.getType()));
    }
    return Map.of("excludedKeys", excludedKeys, "elementKeys", elementKeys);
  }

  private static List<String> getExcludedKeys(Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs, Function<ColumnHeaderFieldPair, String> mapFunc){
    return columnHeaderFieldPairs.stream()
            .filter(ColumnHeaderFieldPair::isExcluded)
            .map(mapFunc)
            .collect(Collectors.toList());
  }

  private static List<String> getElementKeys(Collection<ColumnHeaderFieldPair> columnHeaderFieldPairs, Function<ColumnHeaderFieldPair, String> mapFunc){
    return columnHeaderFieldPairs.stream()
                    .map(mapFunc)
                    .collect(Collectors.toList());
  }

  public static JRDesignElement getDesignElementByKey(JRElementGroup band, String key) {
    return (JRDesignElement) band.getElementByKey(key);
  }

  public static void hideElementByKey(JRBand band, String key) {
    JRDesignElement designElement = getDesignElementByKey(band, key);
    designElement.setPrintWhenExpression(new JRDesignExpression());
  }
}
