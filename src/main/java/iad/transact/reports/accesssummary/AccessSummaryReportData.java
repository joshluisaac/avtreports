package iad.transact.reports.accesssummary;

import iad.reports.ReportData;
import iad.reports.ReportUtils;
import iad.transact.reports.common.HeaderData;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class AccessSummaryReportData implements ReportData {

  private List<AccessSummaryReportEntry> accessSummaryData;
  private HeaderData headerData;
  private String summaryType;
  private String realUserName;
  private LocalDate startDate;
  private LocalDate endDate;


  public String getDatePeriod(LocalDate startDate, LocalDate endDate) {
    return ReportUtils.getDatePeriodString(startDate, endDate);
  }
}
