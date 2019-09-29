package iad.transact.reports.corporateactionelection;

import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import java.util.*;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

public class SuccessfulElectionsCustomiser
        implements Customiser<CorporateActionElectionReportData> {

  private static final List<String> ALL_COLUMN_HEADER_KEYS =
          List.of(
                  "adviserHeaderKey",
                  "investorHeaderKey",
                  "wrapperHeaderKey",
                  "electedProductHeaderKey",
                  "isinHeaderKey",
                  "decisionTypeHeaderKey",
                  "decisionMakerHeaderKey",
                  "previouslyElectedUnitsHeaderKey",
                  "newlyElectedUnitsHeaderKey",
                  "newlyElectedPercentageHeaderKey");
  /*  private static final List<String> MIFID_COLUMN_FIELD_KEYS =
  List.of(
      "adviserFieldKey",
      "investorFieldKey",
      "wrapperFieldKey",
      "electedProductFieldKey",
      "isinFieldKey",
      "decisionTypeFieldKey",
      "decisionMakerFieldKey",
      "previouslyElectedUnitsFieldKey",
      "newlyElectedUnitsFieldKey",
      "newlyElectedPercentageFieldKey");*/

  void getMifidHeaderElements() {}

  @Override
  public void customise(CorporateActionElectionReportData data, JasperDesign design) {
    customiseMifidHeader(data, design);
  }

  private void customiseMifidHeader(CorporateActionElectionReportData data, JasperDesign design) {
    List<String> mifidColumnHeaderKeys = List.of("decisionTypeHeaderKey", "decisionMakerHeaderKey");
    if (!data.isMifidReportable()) {
      // Fetch column header band
      JRBand columnHeaderBand = design.getColumnHeader();
      // Retrieve band elements
      final Map<String, ReportElement> tableHeaderColumns =
              CustomiserHelper.getBandElementsByKeys(columnHeaderBand, ALL_COLUMN_HEADER_KEYS);
      System.out.println(tableHeaderColumns.size());
      // Get mifid table header width
      int tableHeaderWidth =
              tableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      Map<String, ReportElement> nonMifidTableHeaderColumns =
              new HashMap<>(tableHeaderColumns);
      mifidColumnHeaderKeys.forEach(columnKey -> nonMifidTableHeaderColumns.remove(columnKey));
      System.out.println(nonMifidTableHeaderColumns.size());

      int nonMifidTableHeaderWidth =
              nonMifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      System.out.println(tableHeaderWidth);
      System.out.println(nonMifidTableHeaderWidth);

      hideElements(mifidColumnHeaderKeys, columnHeaderBand);

      double rebalanceFactor = (double) tableHeaderWidth / nonMifidTableHeaderWidth;

      Integer[] width = { -1 };

      nonMifidTableHeaderColumns.entrySet().stream()
              .sorted(Map.Entry.comparingByValue(Comparator.comparing(ReportElement::getxAxis)))
              .forEach(
                      e -> {
                        JRDesignElement designElement =
                                CustomiserHelper.getDesignElementByKey(columnHeaderBand, e.getKey());

                        double newWidth = (e.getValue().getWidth() * rebalanceFactor);
                        // double newX = (e.getValue().getxAxis() * rebalanceFactor);
                        designElement.setWidth((int) Math.round(newWidth));

                        if (!e.getKey().equals("adviserHeaderKey")) {
                          System.out.println("Before..." +width[0]);
                          designElement.setX(width[0]);
                        }

                        width[0] = (int) Math.round(newWidth);
                        System.out.println("After..." + width[0]);
                      });
    }
  }

  public void customiseBackup(CorporateActionElectionReportData data, JasperDesign design) {
    List<String> hideColumnHeaderKeys = List.of("decisionTypeHeaderKey", "decisionMakerHeaderKey");
    /*List<String> hideColumnFieldKeys = List.of("decisionTypeFieldKey", "decisionMakerFieldKey");*/
    if (data.isMifidReportable()) {
      JRBand mifidColumnHeaderBand = design.getColumnHeader();

      // Retrieve Mifid table header report elements
      Map<String, ReportElement> mifidTableHeaderColumns =
              CustomiserHelper.getBandElementsByKeys(mifidColumnHeaderBand, ALL_COLUMN_HEADER_KEYS);
      // Get mifid table header width
      int mifidTableHeaderWidth =
              mifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      // hideColumnHeaderKeys.forEach(columnKey -> mifidTableHeaderColumns.remove(columnKey));

      List<String> nonMifidColumnHeaderKeys = new ArrayList<>(ALL_COLUMN_HEADER_KEYS);
      nonMifidColumnHeaderKeys.removeAll(hideColumnHeaderKeys);

      // get NonMifid table header report elements
      Map<String, ReportElement> nonMifidTableHeaderColumns =
              CustomiserHelper.getBandElementsByKeys(mifidColumnHeaderBand, nonMifidColumnHeaderKeys);
      // Get NonMifid table header width
      int nonMifidTableHeaderWidth =
              nonMifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      System.out.println(mifidTableHeaderColumns.size());
      System.out.println(nonMifidTableHeaderColumns.size());

      hideElements(hideColumnHeaderKeys, mifidColumnHeaderBand);

      Double rebalanceFactor = (double) mifidTableHeaderWidth / nonMifidTableHeaderWidth;

      // sort
      // mifidTableHeaderColumns.forEach((k, v) -> System.out.println(k));
      // sortMapByXAxis(mifidTableHeaderColumns).forEach((k, v) -> System.out.println(k));

      nonMifidTableHeaderColumns.entrySet().stream()
              .sorted(Map.Entry.comparingByValue(Comparator.comparing(ReportElement::getxAxis)))
              .forEach(
                      e -> {
                        JRDesignElement designElement =
                                CustomiserHelper.getDesignElementByKey(mifidColumnHeaderBand, e.getKey());

                        if (e.getValue().getxAxis() == 0) {
                          System.out.println(e.getValue().getKey());
                          // rebalance only the width
                          Double newWidth = (e.getValue().getWidth() * rebalanceFactor);
                          System.out.println("New width: " + newWidth);
                          System.out.println("......");
                          // designElement.setWidth((int) Math.round(newWidth));
                          designElement.setWidth(10);
                          // designElement.setX(5);
                          // designElement.setStyleNameReference("TableHeadingBlue");
                        } else {
                          Double newWidth = (e.getValue().getWidth() * rebalanceFactor);
                          Double newX = (e.getValue().getxAxis() * rebalanceFactor);
                          System.out.println("New X: " + newX);
                          System.out.println("New width: " + newWidth);
                          designElement.setX((int) Math.round(newX));
                          designElement.setWidth((int) Math.round(newWidth));
                        }
                      });

      // if factor is 1 do nothing
      // if factor is  > 1 expand columns
      // if factor is < 1 shrink columns

      System.out.println(rebalanceFactor);
      JRDesignElement adviserElement =
              CustomiserHelper.getDesignElementByKey(mifidColumnHeaderBand, "adviserHeaderKey");
      Double x = (80 * rebalanceFactor);
      System.out.println("Actual " + x);

      System.out.println("Trunc " + x.intValue());
      System.out.println("Round " + Math.round(x));

      adviserElement.setWidth(80);
      System.out.println(nonMifidTableHeaderColumns.get("adviserHeaderKey").toString());

      // a report can have one or more groups.
      // Each group must have either a header or footer section
      // Each section is made up of bands

      // check group name to avoid traversing other groups
      /*      if (design.getGroupsList().get(0).getName().equals("ReportGroup")) {
        JRSection successfulElectionsSection =
            design.getGroupsList().get(0).getGroupHeaderSection();
        JRBand successfulElectionsBand = successfulElectionsSection.getBands()[0];
        Map<String, ReportElement> tableFields =
            CustomiserHelper.getBandElementsByKeys(
                successfulElectionsBand, MIFID_COLUMN_FIELD_KEYS);
        int tableFieldsWidth =
            tableFields.values().stream().mapToInt(ReportElement::getWidth).sum();
        System.out.println(tableFieldsWidth);

        hideMifidHeaderColumns(hideColumnFieldKeys, successfulElectionsBand);
      }*/
    }
  }

  private void hideElements(
          List<String> keys, JRBand band) {
    keys.forEach(
            entry -> CustomiserHelper.hideElementByKey(band, entry));
  }

  public static Map<String, ReportElement> sortMapByXAxis(
          final Map<String, ReportElement> unSortedMap) {
    return unSortedMap.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.comparing(ReportElement::getxAxis)))
            .collect(
                    Collectors.toMap(
                            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  void rebalanceElement(JRElement element, String key) {
    element.getKey();
    element.setX(20);
    element.setWidth(7);
  }
}
