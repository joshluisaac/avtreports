package iad.transact.reports.accesssummary;

import iad.reports.ReportData;
import iad.reports.ReportUtils;
import iad.transact.reports.common.HeaderData;
import java.time.LocalDate;
import java.util.List;

public class AccessSummaryReportData implements ReportData {

  private List<AccessSummaryReportEntry> accessSummaryData;
  private HeaderData headerData;
  private String summaryType;
  private String realUserName;
  private LocalDate startDate;
  private LocalDate endDate;

  public AccessSummaryReportData(
      List<AccessSummaryReportEntry> accessSummaryData,
      HeaderData headerData,
      String summaryType,
      String realUserName,
      LocalDate startDate,
      LocalDate endDate) {
    this.accessSummaryData = accessSummaryData;
    this.headerData = headerData;
    this.summaryType = summaryType;
    this.realUserName = realUserName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public HeaderData getHeaderData() {
    return headerData;
  }

  public List<AccessSummaryReportEntry> getAccessSummaryData() {
    return accessSummaryData;
  }

  public String getSummaryType() {
    return summaryType;
  }

  public String getRealUserName() {
    return realUserName;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getDatePeriod(LocalDate startDate, LocalDate endDate) {
    return ReportUtils.getDatePeriodString(startDate, endDate);
  }
}
