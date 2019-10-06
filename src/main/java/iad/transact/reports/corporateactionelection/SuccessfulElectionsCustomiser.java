package iad.transact.reports.corporateactionelection;

import iad.reports.ColumnHeaderFieldPair;
import iad.reports.ColumnType;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;

public class SuccessfulElectionsCustomiser
    implements Customiser<CorporateActionElectionReportData> {

  private static final List<ColumnHeaderFieldPair> COLUMN_FIELD_PAIRS =
      List.of(
          new ColumnHeaderFieldPair("adviserHeaderKey", "adviserFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("investorHeaderKey", "investorFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("wrapperHeaderKey", "wrapperFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair(
              "electedProductHeaderKey", "electedProductFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("isinHeaderKey", "isinFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("decisionTypeHeaderKey", "decisionTypeFieldKey", Boolean.TRUE),
          new ColumnHeaderFieldPair(
              "decisionMakerHeaderKey", "decisionMakerFieldKey", Boolean.TRUE),
          new ColumnHeaderFieldPair(
              "previouslyElectedUnitsHeaderKey", "previouslyElectedUnitsFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair(
              "newlyElectedUnitsHeaderKey", "newlyElectedUnitsFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair(
              "newlyElectedPercentageHeaderKey", "newlyElectedPercentageFieldKey", Boolean.FALSE));

  @Override
  public void customise(CorporateActionElectionReportData data, JasperDesign design) {
    if (!data.isMifidReportable()) {
      JRBand columnHeaderBand = design.getColumnHeader();
      JRBand detailBand = design.getDetailSection().getBands()[0];
      CustomiserHelper.rebalance(columnHeaderBand, COLUMN_FIELD_PAIRS, ColumnType.HEADER);
      CustomiserHelper.rebalance(detailBand, COLUMN_FIELD_PAIRS, ColumnType.DETAIL);
    }
  }
}
