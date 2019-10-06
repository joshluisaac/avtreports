package iad.reports;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ReportUtils {

  private ReportUtils() {}

  private static String getDaySupText(int day) {
    final String SUP_WRAPPER = "<sup>%s</sup>";

    if (day >= 11 && day <= 13) {
      return day + String.format(SUP_WRAPPER, "th");
    }
    switch (day % 10) {
      case 1:
        return day + String.format(SUP_WRAPPER, "st");
      case 2:
        return day + String.format(SUP_WRAPPER, "nd");
      case 3:
        return day + String.format(SUP_WRAPPER, "rd");
      default:
        return day + String.format(SUP_WRAPPER, "th");
    }
  }

  public static String getDatePeriodString(LocalDate startDate, LocalDate endDate) {
    DateTimeFormatter reportDateFormat =
        new DateTimeFormatterBuilder()
            .appendText(MONTH_OF_YEAR)
            .appendLiteral(' ')
            .appendValue(YEAR)
            .toFormatter();
    String startDateText =
        ReportUtils.getDaySupText(startDate.getDayOfMonth())
            + " "
            + startDate.format(reportDateFormat);
    String endDateText =
        ReportUtils.getDaySupText(endDate.getDayOfMonth()) + " " + endDate.format(reportDateFormat);
    return String.format("%s to %s", startDateText, endDateText);
  }
}
