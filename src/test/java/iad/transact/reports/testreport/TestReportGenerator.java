package iad.transact.reports.testreport;

import iad.reports.ReportGenerator;
import iad.transact.reports.TransactReportGenerator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestReportGenerator {

  private TestReportFakeData testReportFakeData;
  private ReportGenerator<TestReportFakeData> reportGenerator;
  private String reportName;

  @Before
  public void beforeTestReportGenerator() {
    reportName = "TestBasicReport";
    TestReportEntry row1 =
        new TestReportEntry("TestNameA", "TestNameA@mail.com", new BigDecimal("120.48"),"04-901-765");
    TestReportEntry row2 =
        new TestReportEntry("TestNameB", "TestNameB@mail.com", new BigDecimal("190.07"),"04-900-765");
    testReportFakeData = new TestReportFakeData(List.of(row1, row2));
    reportGenerator = new TransactReportGenerator<>(reportName, null);
  }

  @Test @SneakyThrows
  public void testShouldGeneratePdfAsStream() {
    InputStream inputStream = reportGenerator.generatePdf(testReportFakeData);
    Assert.assertTrue(inputStream.readAllBytes().length > 0);
  }

  @Test
  public void testShouldGeneratePdfAsByteArrayOutputStream() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    reportGenerator.generatePdf(testReportFakeData, outputStream);
    Assert.assertTrue(outputStream.toByteArray().length > 0);
  }

  @Test @SneakyThrows
  public void testGeneratePdf() {
    try (OutputStream outputStream =
        new FileOutputStream(new File(reportName + "_" + System.currentTimeMillis() + ".pdf"))) {
      reportGenerator.generatePdf(testReportFakeData, outputStream);
    }
  }
}
