package iad.transact.reports;

import iad.reports.Customiser;
import iad.reports.JasperReportGenerator;
import iad.reports.ReportData;
import java.util.List;
import java.util.Map;

public class TransactReportGenerator<T extends ReportData> extends JasperReportGenerator<T> {

  public TransactReportGenerator(
      String reportName, Map<String, Customiser<? super T>> customisers) {
    super(reportName, List.of(reportName, "Common"), customisers);
  }
}
