package iad.transact.reports.testreport;

import iad.reports.ColumnFieldPair;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import java.util.*;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JasperDesign;

public class TestReportCustomiser implements Customiser<TestReportFakeData> {

  private static final List<ColumnFieldPair> COLUMN_FIELD_PAIRS =
      List.of(
          new ColumnFieldPair("nameColumnKey", "nameFieldKey"),
          new ColumnFieldPair("emailColumnKey", "emailFieldKey"),
          new ColumnFieldPair("osBalColumnKey", "osBalFieldKey"),
          new ColumnFieldPair("currentDateColumnKey", "currentDateFieldKey"),
          new ColumnFieldPair("phoneNumberColumnKey", "phoneNumberFieldKey"));

  @Override
  public void customise(TestReportFakeData data, JasperDesign design) {
    JRBand columnHeaderBand = design.getColumnHeader();
    JRBand detailBand = design.getDetailSection().getBands()[0];
    List<String> columnKeys = new ArrayList<>();
    COLUMN_FIELD_PAIRS.forEach(key -> columnKeys.add(key.getColumnKey()));
    List<String> fieldKeys = new ArrayList<>();
    COLUMN_FIELD_PAIRS.forEach(key -> fieldKeys.add(key.getFieldKey()));
      rebalance(
        columnHeaderBand, List.of("osBalColumnKey", "currentDateColumnKey"), columnKeys);
      rebalance(detailBand, List.of("osBalFieldKey", "currentDateFieldKey"), fieldKeys);
  }

  private void rebalance(JRBand band, List<String> excludeKeys, List<String> elementKeys) {
    List<JRElement> bandElements = CustomiserHelper.getBandElementsByKeys(band, elementKeys);
    int tableHeaderWidth = bandElements.stream().mapToInt(JRElement::getWidth).sum();
    List<JRElement> nonMifidTableElements = CustomiserHelper.remove(excludeKeys, bandElements);
    int nonMifidTableHeaderWidth =
        nonMifidTableElements.stream().mapToInt(JRElement::getWidth).sum();
    double rebalanceFactor = (double) tableHeaderWidth / nonMifidTableHeaderWidth;
    CustomiserHelper.hideElements(excludeKeys, band);
    CustomiserHelper.updateElement(nonMifidTableElements, rebalanceFactor);
  }


}
