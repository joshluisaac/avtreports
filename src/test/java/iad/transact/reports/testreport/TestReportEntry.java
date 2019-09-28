package iad.transact.reports.testreport;

import java.math.BigDecimal;

public class TestReportEntry {

  private String name;
  private String email;
  private BigDecimal outstandingBalance;
  private String phoneNumber;

  public TestReportEntry(String name, String email, BigDecimal outstandingBalance, String phoneNumber) {
    this.name = name;
    this.email = email;
    this.outstandingBalance = outstandingBalance;
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public BigDecimal getOutstandingBalance() {
    return outstandingBalance;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
