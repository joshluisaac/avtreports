package iad.transact.reports;

import iad.reports.ReportGenerator;
import iad.transact.reports.accesssummary.AccessSummaryReportData;
import iad.transact.reports.corporateactionelection.CorporateActionElectionReportData;
import iad.transact.reports.corporateactionelection.SuccessfulElectionsCustomiser;

import java.util.Map;

public final class TransactReports {

  public static final ReportGenerator<AccessSummaryReportData> ACCESS_SUMMARY =
      new TransactReportGenerator<>("AccessSummary", null);

  public static final ReportGenerator<CorporateActionElectionReportData> CORPORATE_ACTION_ELECTION =
      new TransactReportGenerator<>("CorporateActionElection", Map.of("CorporateActionElection/Subreports/MifidSuccessfulElections.jasper", new SuccessfulElectionsCustomiser()));

  private TransactReports() {}
}
