package iad.transact.reports.testreport;

import iad.reports.ColumnHeaderFieldPair;
import iad.reports.ColumnType;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import java.util.*;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.design.JasperDesign;

public class TestReportCustomiser implements Customiser<TestReportFakeData> {

  private static final List<ColumnHeaderFieldPair> COLUMN_FIELD_PAIRS =
      List.of(
          new ColumnHeaderFieldPair("nameColumnKey", "nameFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("emailColumnKey", "emailFieldKey", Boolean.FALSE),
          new ColumnHeaderFieldPair("osBalColumnKey", "osBalFieldKey", Boolean.TRUE),
          new ColumnHeaderFieldPair("currentDateColumnKey", "currentDateFieldKey", Boolean.TRUE),
          new ColumnHeaderFieldPair("phoneNumberColumnKey", "phoneNumberFieldKey", Boolean.FALSE));

  @Override
  public void customise(TestReportFakeData data, JasperDesign design) {
    JRBand columnHeaderBand = design.getColumnHeader();
    JRBand detailBand = design.getDetailSection().getBands()[0];
    CustomiserHelper.rebalance(columnHeaderBand, COLUMN_FIELD_PAIRS, ColumnType.HEADER);
    CustomiserHelper.rebalance(detailBand, COLUMN_FIELD_PAIRS, ColumnType.DETAIL);
  }
}
