package iad.transact.reports.accesssummary;

import iad.transact.reports.common.HeaderCustomiserData;
import iad.transact.reports.common.HeaderData;
import java.util.List;

public class AccessSummaryReportData implements HeaderCustomiserData {

  private List<AccessSummaryReportEntry> accessSummaryData;
  private HeaderCustomiserData headerCustomiserData;
  private String summaryType;
  private String realUserName;
  private String datePeriod;

  public AccessSummaryReportData(
      List<AccessSummaryReportEntry> accessSummaryData,
      HeaderCustomiserData headerCustomiserData,
      String summaryType,
      String realUserName,
      String datePeriod) {
    this.accessSummaryData = accessSummaryData;
    this.headerCustomiserData = headerCustomiserData;
    this.summaryType = summaryType;
    this.realUserName = realUserName;
    this.datePeriod = datePeriod;
  }

  @Override
  public HeaderData getHeaderData() {
    return headerCustomiserData.getHeaderData();
  }

  public List<AccessSummaryReportEntry> getAccessSummaryData() {
    return accessSummaryData;
  }

  public HeaderCustomiserData getHeaderCustomiserData() {
    return headerCustomiserData;
  }

  public String getSummaryType() {
    return summaryType;
  }

  public String getRealUserName() {
    return realUserName;
  }

  public String getDatePeriod() {
    return datePeriod;
  }
}
