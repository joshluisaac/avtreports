package iad.transact.reports.corporateactionelection;

import iad.reports.Customiser;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuccessfulElectionsCustomiser
    implements Customiser<CorporateActionElectionReportData> {


  @Override
  public void customise(CorporateActionElectionReportData data, JasperDesign design) {
    if (data.isMifidReportable()) {
      JRBand columnHeaderBand = design.getColumnHeader();
      List<ReportElement> tableHeaderColumns = getReportElements(columnHeaderBand);
      int tableHeaderWidth = tableHeaderColumns.stream().mapToInt(x -> x.getWidth()).sum();
      System.out.println(tableHeaderWidth);

      List.of("decisionTypeColumnHeaderKey","decisionMakerColumnHeaderKey").forEach(entry -> removeElementByKey(columnHeaderBand,entry));

      int tableHeaderWidthAfterElementsRemoval = tableHeaderColumns.stream().mapToInt(x -> x.getWidth()).sum();
      System.out.println("After..." + tableHeaderWidthAfterElementsRemoval);

      //a report can have one or more groups.
      //Each group must have either a header or footer section
      //Each section is made up of bands

      //check group name to avoid traversing other groups
      if (design.getGroupsList().get(0).getName().equals("ReportGroup")) {
        JRSection successfulElectionsSection = design.getGroupsList().get(0).getGroupHeaderSection();
        JRBand successfulElectionsBand = successfulElectionsSection.getBands()[0];
        List<ReportElement> tableFields  = getReportElements(successfulElectionsBand);
        int tableFieldsWidth = tableFields.stream().mapToInt(x -> x.getWidth()).sum();
        System.out.println(tableFieldsWidth);

        List.of("decisionTypeFieldKey","decisionMakerFieldKey").forEach(entry -> removeElementByKey(successfulElectionsBand,entry));

      }
    }
  }


  private JRDesignElement getElementByKey(JRBand band, String key) {
    return (JRDesignElement) band.getElementByKey(key);
  }

  private void removeElementByKey(JRBand band, String key) {
    JRDesignElement designElement = getElementByKey(band,key);
    designElement.setPrintWhenExpression(new JRDesignExpression());
  }

  private List<ReportElement> getReportElements(JRBand band){
    List<JRElement> bandElements = Arrays.asList(band.getElements());
    List<ReportElement> reportElements = new ArrayList<>();
    bandElements.forEach(
            (row) -> {
              ReportElement coordinate =
                      new ReportElement(
                              row.getKey(), row.getX(), row.getY(), row.getWidth(), row.getHeight());
              reportElements.add(coordinate);
            });
    return reportElements;

  }

  void rebalanceElement(JRBand band, String key) {

  }




}
