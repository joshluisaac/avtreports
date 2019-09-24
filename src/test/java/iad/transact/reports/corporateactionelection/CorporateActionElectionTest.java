package iad.transact.reports.corporateactionelection;

import iad.reports.ReportGenerator;
import iad.transact.reports.TransactReports;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

public class CorporateActionElectionTest {

  private CorporateActionElectionReportData reportData;
  private ReportGenerator<CorporateActionElectionReportData> corporateActionElection;

  @Before
  public void beforeCorporateActionElectionTest() {
    reportData = CorporateActionElectionFakeData.buildReportData();
    corporateActionElection = TransactReports.CORPORATE_ACTION_ELECTION;
  }

  @Test @SneakyThrows
  public void testGenerateCorporateActionElectionPdf() {
    try (OutputStream outputStream =
        new FileOutputStream(
            new File("CorporateActionElection" + "_" + System.currentTimeMillis() + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }
}
