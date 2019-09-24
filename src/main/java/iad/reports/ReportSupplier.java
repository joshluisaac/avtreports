package iad.reports;

public interface ReportSupplier {

  byte[] getReport(String reportId);

  boolean isReportFileName(String fileName);
}
