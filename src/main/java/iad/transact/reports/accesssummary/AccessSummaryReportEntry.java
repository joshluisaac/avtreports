package iad.transact.reports.accesssummary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessSummaryReportEntry {

  private String loggedInAs;
  private String accessed;
  @JsonDeserialize(using = AccessSummaryEventDateTimeHandler.class)
  private LocalDateTime eventDateTime;
  private String eventDisplayMessage;
  private String confirmationKey;


}
