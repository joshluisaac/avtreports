package iad.transact.reports.accesssummary;

public class AccessSummaryReportEntry {

  private String loggedInAs;
  private String accessed;
  private String eventDateTime;
  private String eventDisplayMessage;
  private String confirmationKey;

  private AccessSummaryReportEntry() {}

  private AccessSummaryReportEntry(Builder builder) {
    this.loggedInAs = builder.loggedInAs;
    this.accessed = builder.accessed;
    this.eventDateTime = builder.eventDateTime;
    this.eventDisplayMessage = builder.eventDisplayMessage;
    this.confirmationKey = builder.confirmationKey;
  }

  public static class Builder {
    private String loggedInAs;
    private String accessed;
    private String eventDateTime;
    private String eventDisplayMessage;
    private String confirmationKey;

    public Builder setLoggedInAs(String loggedInAs) {
      this.loggedInAs = loggedInAs;
      return this;
    }

    public Builder setAccessed(String accessed) {
      this.accessed = accessed;
      return this;
    }

    public Builder setEventDateTime(String eventDateTime) {
      this.eventDateTime = eventDateTime;
      return this;
    }

    public Builder setEventDisplayMessage(String eventDisplayMessage) {
      this.eventDisplayMessage = eventDisplayMessage;
      return this;
    }

    public Builder setConfirmationKey(String confirmationKey) {
      this.confirmationKey = confirmationKey;
      return this;
    }

    public AccessSummaryReportEntry build() {
      return new AccessSummaryReportEntry(this);
    }
  }

  public String getLoggedInAs() {
    return loggedInAs;
  }

  public String getAccessed() {
    return accessed;
  }

  public String getEventDateTime() {
    return eventDateTime;
  }

  public String getEventDisplayMessage() {
    return eventDisplayMessage;
  }

  public String getConfirmationKey() {
    return confirmationKey;
  }
}
