package iad.transact.reports.corporateactionelection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class CorporateActionElectionFakeData {

  private static final String PAYLOAD =
      "datasets/CorporateActionElection/CorporateActionElectionSampleDataSet.json";

  @SneakyThrows
  private static Function<String, JsonNode> getJsonMainNode() {
    InputStream inputStream =
            Thread.currentThread().getContextClassLoader().getResourceAsStream(PAYLOAD);
    JsonNode node = new ObjectMapper().readTree(inputStream);
    return  (key) -> node.path(key);
  }


  @SneakyThrows
  private static List<CorporateActionElectionEntry> getElections(JsonNode node) {
    return new ObjectMapper()
        .readValue(node.toString(), new TypeReference<List<CorporateActionElectionEntry>>() {});
  }

  private static String getFooterText() {
    DateTimeFormatter reportFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
    return String.format(
        "Corporate Action Election added by %s at %s",
        "TestUser", LocalDateTime.now().format(reportFormat));
  }

  /**
   * Builds and returns corporate action election report data.
   *
   * @return corporate action report data
   */
  public static CorporateActionElectionReportData buildReportData() {
    Function<String, JsonNode> mainNodeFunction = CorporateActionElectionFakeData.getJsonMainNode();
    JsonNode mainNode = mainNodeFunction.apply("Main");

    List<CorporateActionElectionEntry> successfulElections =
        CorporateActionElectionFakeData.getElections(mainNode.path("successfulElections"));
    List<CorporateActionElectionEntry> failedElections =
        CorporateActionElectionFakeData.getElections(mainNode.path("failedElections"));
    boolean isMifidReportable = Boolean.parseBoolean(mainNode.path("isMifidReportable").asText());
    String corporateActionName = mainNode.path("corporateActionName").asText();
    String electionEndDate = mainNode.path("electionEndDate").asText();
    String electionOnDate = mainNode.path("electionOnDate").asText();
    String successfulOfTotalElectionSize =
        mainNode.path("successfulElectionSize").asText()
            + " of "
            + mainNode.path("totalElectionSize").asText();
    CorporateActionElectionReportHeaderData headerData =
        new CorporateActionElectionReportHeaderData(
            corporateActionName, electionEndDate, electionOnDate, successfulOfTotalElectionSize);
    return new CorporateActionElectionReportData(
        successfulElections, failedElections, isMifidReportable, headerData, getFooterText());
  }
}
