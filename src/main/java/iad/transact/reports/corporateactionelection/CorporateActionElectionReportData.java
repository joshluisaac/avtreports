package iad.transact.reports.corporateactionelection;

import iad.reports.ReportData;
import java.util.List;

public class CorporateActionElectionReportData implements ReportData {

  private final List<CorporateActionElectionEntry> successfulElections;
  private final List<CorporateActionElectionEntry> failedElections;
  private final boolean isMifidReportable;
  private final CorporateActionElectionReportHeaderData headerData;
  private final String footerText;

  public CorporateActionElectionReportData(
      List<CorporateActionElectionEntry> successfulElections,
      List<CorporateActionElectionEntry> failedElections,
      boolean isMifidReportable,
      CorporateActionElectionReportHeaderData headerData,
      String footerText) {
    this.successfulElections = successfulElections;
    this.failedElections = failedElections;
    this.isMifidReportable = isMifidReportable;
    this.headerData = headerData;
    this.footerText = footerText;
  }

  private CorporateActionElectionReportData(Builder builder) {
    this.successfulElections = builder.successfulElections;
    this.failedElections = builder.failedElections;
    this.isMifidReportable = builder.isMifidReportable;
    this.headerData = builder.headerData;
    this.footerText = builder.footerText;
  }

  public List<CorporateActionElectionEntry> getSuccessfulElections() {
    return successfulElections;
  }

  public List<CorporateActionElectionEntry> getFailedElections() {
    return failedElections;
  }

  public boolean isMifidReportable() {
    return isMifidReportable;
  }

  public CorporateActionElectionReportHeaderData getHeaderData() {
    return headerData;
  }

  public String getFooterText() {
    return footerText;
  }

  public static final class Builder {
    private List<CorporateActionElectionEntry> successfulElections;
    private List<CorporateActionElectionEntry> failedElections;
    private boolean isMifidReportable;
    private CorporateActionElectionReportHeaderData headerData;
    private String footerText;

    public Builder setSuccessfulElections(List<CorporateActionElectionEntry> successfulElections) {
      this.successfulElections = successfulElections;
      return this;
    }

    public Builder setFailedElections(List<CorporateActionElectionEntry> failedElections) {
      this.failedElections = failedElections;
      return this;
    }

    public Builder setMifidReportable(boolean mifidReportable) {
      isMifidReportable = mifidReportable;
      return this;
    }

    public Builder setHeaderData(CorporateActionElectionReportHeaderData headerData) {
      this.headerData = headerData;
      return this;
    }

    public Builder setFooterText(String footerText) {
      this.footerText = footerText;
      return this;
    }

    public CorporateActionElectionReportData build() {
      return new CorporateActionElectionReportData(this);
    }
  }
}
