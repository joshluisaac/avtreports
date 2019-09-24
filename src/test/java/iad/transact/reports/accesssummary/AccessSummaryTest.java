package iad.transact.reports.accesssummary;

import iad.reports.ReportGenerator;
import iad.transact.reports.TransactReports;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

public class AccessSummaryTest {

  private AccessSummaryReportData accessSummaryReportData;
  private ReportGenerator<AccessSummaryReportData> accessSummayReport;

  @Before
  public void beforeAccessSummaryTest() {
    accessSummaryReportData = AccessSummaryReportFakeData.buildReportData();
    accessSummayReport = TransactReports.ACCESS_SUMMARY;
  }

  @Test @SneakyThrows
  public void testGenerateAccessSummaryReportFromOutputStream() {
    try (OutputStream outputStream =
        new FileOutputStream(
            new File("AccessSummary" + "_" + System.currentTimeMillis() + ".pdf"))) {
      accessSummayReport.generatePdf(accessSummaryReportData, outputStream);
    }
  }
}
