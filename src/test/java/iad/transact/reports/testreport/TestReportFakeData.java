package iad.transact.reports.testreport;

import iad.reports.ReportData;
import java.util.List;

public class TestReportFakeData implements ReportData {

  private List<TestReportEntry> testReportEntries;

  public TestReportFakeData(List<TestReportEntry> testReportEntries) {
    this.testReportEntries = testReportEntries;
  }

  public List<TestReportEntry> getTestReportEntries() {
    return testReportEntries;
  }
}
