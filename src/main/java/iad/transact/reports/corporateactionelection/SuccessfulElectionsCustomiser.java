package iad.transact.reports.corporateactionelection;

import iad.reports.ColumnFieldPair;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import java.util.*;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

public class SuccessfulElectionsCustomiser
        implements Customiser<CorporateActionElectionReportData> {

  private static final List<ColumnFieldPair> COLUMN_FIELD_PAIRS = List.of(
          new ColumnFieldPair("adviserHeaderKey","adviserFieldKey")
          ,new ColumnFieldPair("adviserHeaderKey","adviserFieldKey")
          ,new ColumnFieldPair("investorHeaderKey","investorFieldKey")
          ,new ColumnFieldPair("wrapperHeaderKey","wrapperFieldKey")
          ,new ColumnFieldPair("electedProductHeaderKey","electedProductFieldKey")
          ,new ColumnFieldPair("isinHeaderKey","isinFieldKey")
          ,new ColumnFieldPair("decisionTypeHeaderKey","decisionTypeFieldKey")
          ,new ColumnFieldPair("decisionMakerHeaderKey","decisionMakerFieldKey")
          ,new ColumnFieldPair("previouslyElectedUnitsHeaderKey","previouslyElectedUnitsFieldKey")
          ,new ColumnFieldPair("newlyElectedUnitsHeaderKey","newlyElectedUnitsFieldKey")
          ,new ColumnFieldPair("newlyElectedPercentageHeaderKey","newlyElectedPercentageFieldKey")

  );


  @Override
  public void customise(CorporateActionElectionReportData data, JasperDesign design) {
    JRBand columnHeaderBand = design.getColumnHeader();
    JRBand detailBand = design.getDetailSection().getBands()[0];
  }


}
