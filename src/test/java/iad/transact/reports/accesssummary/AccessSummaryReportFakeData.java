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
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;

public class AccessSummaryReportFakeData {

  @SneakyThrows
  private static List<AccessSummaryReportEntry> getAccessSummaryReportData() {
    InputStream inputStream =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("datasets/AccessSummary/AccessSummaryDataSet.json");
    JsonNode parent = new ObjectMapper().readTree(inputStream);
    return new ObjectMapper()
        .readValue(
            parent.path("Main").path("data").toString(),
            new TypeReference<List<AccessSummaryReportEntry>>() {});
  }

  @SneakyThrows
  private static HeaderData getHeaderData(String graphicContent, String brandingHeaderText) {
    return HeaderData
            .builder()
            .headerImage (ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(graphicContent))))
            .headerText(brandingHeaderText)
            .build();
  }

  public static AccessSummaryReportData buildReportData() {
    return new AccessSummaryReportData(
        AccessSummaryReportFakeData.getAccessSummaryReportData(),
        AccessSummaryReportFakeData.getHeaderData(
            FakeBrandingHeaderImage.getGraphicText(), FakeBrandingHeader.getText()),
        "All Events",
        "Marc O'Hara",
        LocalDate.now().minusMonths(10),
        LocalDate.now());
  }
}
