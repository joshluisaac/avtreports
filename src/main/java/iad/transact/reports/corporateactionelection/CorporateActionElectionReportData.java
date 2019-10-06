package iad.transact.reports.corporateactionelection;

import iad.reports.ReportData;
import lombok.Value;

import java.util.List;

@Value
public class CorporateActionElectionReportData implements ReportData {

  private final List<CorporateActionElectionEntry> successfulElections;
  private final List<CorporateActionElectionEntry> failedElections;
  private final boolean isMifidReportable;
  private final CorporateActionElectionReportHeaderData headerData;
  private final String footerText;


}
