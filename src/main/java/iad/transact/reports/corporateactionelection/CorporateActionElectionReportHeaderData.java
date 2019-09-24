package iad.transact.reports.corporateactionelection;

public class CorporateActionElectionReportHeaderData {

  private String corporateActionName;
  private String electionEndDate;
  private String electionOnDate;
  private String successfulOfTotalElectionSize;

  public CorporateActionElectionReportHeaderData(
      String corporateActionName,
      String electionEndDate,
      String electionOnDate,
      String successfulOfTotalElectionSize) {
    this.corporateActionName = corporateActionName;
    this.electionEndDate = electionEndDate;
    this.electionOnDate = electionOnDate;
    this.successfulOfTotalElectionSize = successfulOfTotalElectionSize;
  }

  public String getCorporateActionName() {
    return corporateActionName;
  }

  public String getElectionEndDate() {
    return electionEndDate;
  }

  public String getElectionOnDate() {
    return electionOnDate;
  }

  public String getSuccessfulOfTotalElectionSize() {
    return successfulOfTotalElectionSize;
  }
}
