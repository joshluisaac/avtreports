package iad.transact.reports.corporateactionelection;

import lombok.Value;

@Value
public class CorporateActionElectionReportHeaderData {

  private String corporateActionName;
  private String electionEndDate;
  private String electionOnDate;
  private String successfulOfTotalElectionSize;


}
