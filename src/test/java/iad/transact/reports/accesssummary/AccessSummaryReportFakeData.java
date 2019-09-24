package iad.transact.reports.accesssummary;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iad.transact.reports.common.FakeBrandingHeader;
import iad.transact.reports.common.FakeBrandingHeaderImage;
import iad.transact.reports.common.HeaderData;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;

public class AccessSummaryReportFakeData {

  @SneakyThrows
  public static List<AccessSummaryReportEntry> getAccessSummaryReportData() {
    InputStream inputStream =
            Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("accesssummary/accessSummaryData.json");
    JsonNode parent = new ObjectMapper().readTree(inputStream);
    return new ObjectMapper()
            .readValue(
                    parent.path("Main").path("data").toString(),
                    new TypeReference<List<AccessSummaryReportEntry>>() {});
  }

  @SneakyThrows
  public static HeaderData getHeaderData(String graphicContent, String brandingHeaderText) {
    return new HeaderData.Builder()
        .setHeaderImage(
            ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(graphicContent))))
        .setHeaderText(brandingHeaderText)
        .build();
  }

  @SneakyThrows
  public static AccessSummaryReportData buildReportData() {
    return new AccessSummaryReportData(
        AccessSummaryReportFakeData.getAccessSummaryReportData(),
        () ->
            AccessSummaryReportFakeData.getHeaderData(
                FakeBrandingHeaderImage.getGraphicText(), FakeBrandingHeader.getText()),
        "All Events",
        "Marc O'Hara",
        "1st October 2018 to 31st October 2018");
  }
}
