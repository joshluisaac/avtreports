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

  private static final List<String> MIFID_COLUMN_HEADER_KEYS =
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

  void getMifidHeaderElements(){

  }

  @Override
  public void customise(CorporateActionElectionReportData data, JasperDesign design) {
    customiseMifidHeader(data,design);

  }

  private void customiseMifidHeader(CorporateActionElectionReportData data, JasperDesign design){
    List<String> hideColumnHeaderKeys = List.of("decisionTypeHeaderKey", "decisionMakerHeaderKey");
    if (data.isMifidReportable()) {
      //Fetch band
      JRBand mifidColumnHeaderBand = design.getColumnHeader();
      // Retrieve Mifid table header report elements from band
      final Map<String, ReportElement> mifidTableHeaderColumns =
          CustomiserHelper.getBandElementsByKeys(mifidColumnHeaderBand, MIFID_COLUMN_HEADER_KEYS);
      System.out.println(mifidTableHeaderColumns.size());
      // Get mifid table header width
      int mifidTableHeaderWidth =
          mifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      Map<String, ReportElement> nonMifidTableHeaderColumns = new HashMap<>(mifidTableHeaderColumns);
      hideColumnHeaderKeys.forEach(columnKey -> nonMifidTableHeaderColumns.remove(columnKey));
      System.out.println(nonMifidTableHeaderColumns.size());

      int nonMifidTableHeaderWidth =
          nonMifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      System.out.println(mifidTableHeaderWidth);
      System.out.println(nonMifidTableHeaderWidth);

      hideMifidHeaderColumns(hideColumnHeaderKeys, mifidColumnHeaderBand);

      Double rebalanceFactor = (double) mifidTableHeaderWidth / nonMifidTableHeaderWidth;

      nonMifidTableHeaderColumns.entrySet().stream()
          .sorted(Map.Entry.comparingByValue(Comparator.comparing(ReportElement::getxAxis)))
          .forEach(
              e -> {
                JRDesignElement designElement =
                    CustomiserHelper.getDesignElementByKey(mifidColumnHeaderBand, e.getKey());
                Double newWidth = (e.getValue().getWidth() * rebalanceFactor);
                Double newX = (e.getValue().getxAxis() * rebalanceFactor);
                designElement.setWidth((int) Math.round(newWidth));
                designElement.setX((int) Math.round(newX));

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
          CustomiserHelper.getBandElementsByKeys(mifidColumnHeaderBand, MIFID_COLUMN_HEADER_KEYS);
      // Get mifid table header width
      int mifidTableHeaderWidth =
          mifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      //hideColumnHeaderKeys.forEach(columnKey -> mifidTableHeaderColumns.remove(columnKey));


      List<String> nonMifidColumnHeaderKeys = new ArrayList<>(MIFID_COLUMN_HEADER_KEYS);
      nonMifidColumnHeaderKeys.removeAll(hideColumnHeaderKeys);

      // get NonMifid table header report elements
      Map<String, ReportElement> nonMifidTableHeaderColumns =
          CustomiserHelper.getBandElementsByKeys(mifidColumnHeaderBand, nonMifidColumnHeaderKeys);
      // Get NonMifid table header width
      int nonMifidTableHeaderWidth =
          nonMifidTableHeaderColumns.values().stream().mapToInt(ReportElement::getWidth).sum();

      System.out.println(mifidTableHeaderColumns.size());
      System.out.println(nonMifidTableHeaderColumns.size());

      hideMifidHeaderColumns(hideColumnHeaderKeys, mifidColumnHeaderBand);

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
                  //designElement.setWidth((int) Math.round(newWidth));
                  designElement.setWidth(10);
                  //designElement.setX(5);
                  //designElement.setStyleNameReference("TableHeadingBlue");
                } else {
                  Double newWidth = (e.getValue().getWidth() * rebalanceFactor);
                  Double newX = (e.getValue().getxAxis() * rebalanceFactor);
                  System.out.println("New X: " + newX);
                  System.out.println("New width: " + newWidth);
                  designElement.setX((int) Math.round(newX));
                  designElement.setWidth((int) Math.round(newWidth));
                }
              });

      //if factor is 1 do nothing
      //if factor is  > 1 expand columns
      //if factor is < 1 shrink columns

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

  private void hideMifidHeaderColumns(List<String> hideColumnHeaderKeys,
      JRBand mifidColumnHeaderBand) {
    hideColumnHeaderKeys.forEach(
        entry -> CustomiserHelper.hideElementByKey(mifidColumnHeaderBand, entry));
  }


  public static Map<String, ReportElement> sortMapByXAxis(final Map<String, ReportElement> unSortedMap) {
    return unSortedMap.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.comparing(ReportElement::getxAxis)))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }




  void rebalanceElement(JRElement element, String key) {
    element.getKey();
    element.setX(20);
    element.setWidth(7);
  }
}
