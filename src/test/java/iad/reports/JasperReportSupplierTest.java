package iad.reports;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JasperReportSupplierTest {

  private ReportSupplier reportSupplier;

  @Rule public ExpectedException expectedExpThrown = ExpectedException.none();
  private static final String REPORT_ID_NOT_EXIST = "TestReport1/thisReportDoesNotExist.jasper";
  private static final String REPORT_ID = "TestReport2/TestReport2_ForTestPurposes.jasper";
  private static final String COMMON_REPORT_ID =
      "TestReportCommon/CommonReport_ForTestPurposes.jasper";
  private List<String> allowedReportDirs;

  @Before
  public void beforeJasperReportSupplierTest() {
    allowedReportDirs = List.of("TestReport1", "TestReportCommon");
    reportSupplier = new JasperReportSupplier<>(null, null, allowedReportDirs);
  }

  @Test
  public void testReportSizeShouldBeGreatThanZero() {
    assertTrue(reportSupplier.getReport(COMMON_REPORT_ID).length > 0);
  }

  /** TestReport1 trying to access TestReport2 throws exception */
  @Test
  public void testShouldThrowExceptionWhenAccessingSubReportOutsideValidDirs() {
    String expectedMessage =
        "The requested report (" + REPORT_ID + ") does not exist within the allowed paths.";
    expectedExpThrown.expect(IllegalArgumentException.class);
    expectedExpThrown.expectMessage(expectedMessage);
    reportSupplier.getReport(REPORT_ID);
  }

  @Test
  public void testShouldThrowExceptionForNonExistentSubReport() {
    String expectedMessage =
        "Please ensure the specified report ("
            + REPORT_ID_NOT_EXIST
            + ") resolves to an actual file";
    expectedExpThrown.expect(IllegalArgumentException.class);
    expectedExpThrown.expectMessage(expectedMessage);
    reportSupplier.getReport(REPORT_ID_NOT_EXIST);
  }

  @Test
  public void testShouldThrowExceptionForIllegalCustomiserKey() {
    String expectedMessage =
        "The requested report (" + REPORT_ID_NOT_EXIST + ") is not a valid path.";
    expectedExpThrown.expect(IllegalArgumentException.class);
    expectedExpThrown.expectMessage(expectedMessage);
    reportSupplier =
        new JasperReportSupplier<>(
            Map.of(REPORT_ID_NOT_EXIST, new TestCustomiser()), null, allowedReportDirs);
  }

  @Test
  public void testShouldReturnTrueIfReportFileNameEndsWithJasper() {
    assertTrue(reportSupplier.isReportFileName(REPORT_ID));
  }
}
