package iad.transact.reports.accesssummary;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccessSummaryEventDateTimeHandler extends StdDeserializer<LocalDateTime> {

  public AccessSummaryEventDateTimeHandler() {
    this(null);
  }

  public AccessSummaryEventDateTimeHandler(Class<LocalDateTime> vc) {
    super(vc);
  }

  @Override @SneakyThrows
  public LocalDateTime deserialize(JsonParser parser, DeserializationContext ctxt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    return LocalDateTime.parse(padMillSecFragment(parser.getText()), formatter);
  }

  private String padMillSecFragment(String dateText) {
    List<String> dateTimeFragments = List.of(dateText.split("\\."));
    if (dateTimeFragments.size() == 1) {
      return dateTimeFragments.get(0) + "." + "000";
    }
    String dateTimeText = dateTimeFragments.get(0);
    String millSecText = dateTimeFragments.get(1);
    if (millSecText.length() > 2) {
      return dateText;
    } else {
      String newMillSecText =
          millSecText.length() == 2 ? ("0" + millSecText) : ("00" + millSecText);
      return dateTimeText + "." + newMillSecText;
    }
  }
}
