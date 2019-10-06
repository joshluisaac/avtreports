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
    corporateActionElection = TransactReports.CORPORATE_ACTION_ELECTION;
  }

  @Test @SneakyThrows
  public void testGenerateNonMifidElectionsPdf() {
    String dataSet = "datasets/CorporateActionElection/NonMifidDataSet.json";
    reportData =
        CorporateActionElectionFakeData.buildReportData(
            CorporateActionElectionFakeData.getJsonMainNode(dataSet));
    try (OutputStream outputStream =
        new FileOutputStream(
            new File(
                "CorporateActionElection_NonMifidSuccessful_"
                    + System.currentTimeMillis()
                    + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }

  @Test @SneakyThrows
  public void testGenerateMifidElections() {
    String dataSet = "datasets/CorporateActionElection/MifidDataSet.json";
    reportData =
        CorporateActionElectionFakeData.buildReportData(
            CorporateActionElectionFakeData.getJsonMainNode(dataSet));
    try (OutputStream outputStream =
        new FileOutputStream(
            new File(
                "CorporateAction_MifidSuccessfulElections_"
                    + System.currentTimeMillis()
                    + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }

  @Test @SneakyThrows
  public void testGenerateZeroFailedElections() {
    String dataSet = "datasets/CorporateActionElection/ZeroFailedElection.json";
    reportData =
        CorporateActionElectionFakeData.buildReportData(
            CorporateActionElectionFakeData.getJsonMainNode(dataSet));
    try (OutputStream outputStream =
        new FileOutputStream(
            new File(
                "CorporateAction_ZeroFailedElections_" + System.currentTimeMillis() + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }

  @Test @SneakyThrows
  public void testGenerateZeroFailedZeroSuccessfulElections() {
    String dataSet = "datasets/CorporateActionElection/ZeroFailedZeroSuccessful.json";
    reportData =
        CorporateActionElectionFakeData.buildReportData(
            CorporateActionElectionFakeData.getJsonMainNode(dataSet));
    try (OutputStream outputStream =
        new FileOutputStream(
            new File(
                "CorporateAction_ZeroFailedZeroSuccessfulElections_"
                    + System.currentTimeMillis()
                    + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }

  @Test @SneakyThrows
  public void testGenerateZeroSuccessfulElections() {
    String dataSet = "datasets/CorporateActionElection/ZeroSuccessfulElection.json";
    reportData =
        CorporateActionElectionFakeData.buildReportData(
            CorporateActionElectionFakeData.getJsonMainNode(dataSet));
    try (OutputStream outputStream =
        new FileOutputStream(
            new File(
                "CorporateAction_ZeroSuccessfulElection_" + System.currentTimeMillis() + ".pdf"))) {
      corporateActionElection.generatePdf(reportData, outputStream);
    }
  }
}
