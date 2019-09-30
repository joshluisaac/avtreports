package iad.transact.reports.testreport;

import iad.reports.ColumnFieldPair;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.*;
import java.util.stream.IntStream;

public class TestReportCustomiser implements Customiser<TestReportFakeData> {

    private static final List<ColumnFieldPair> COLUMN_FIELD_PAIRS =
            List.of(new ColumnFieldPair("nameColumnKey","nameFieldKey")
                    ,new ColumnFieldPair("emailColumnKey","emailFieldKey")
                    ,new ColumnFieldPair("osBalColumnKey","osBalFieldKey")
                    ,new ColumnFieldPair("currentDateColumnKey","currentDateFieldKey")
                    ,new ColumnFieldPair("phoneNumberColumnKey","phoneNumberFieldKey")
    );


    @Override
    public void customise(TestReportFakeData data, JasperDesign design) {
        JRBand columnHeaderBand = design.getColumnHeader();
        JRBand detailBand = design.getDetailSection().getBands()[0];
        List<String> columnKeys = new ArrayList<>();
        COLUMN_FIELD_PAIRS.forEach(key -> columnKeys.add(key.getColumnKey()));
        List<String> fieldKeys = new ArrayList<>();
        COLUMN_FIELD_PAIRS.forEach(key -> fieldKeys.add(key.getFieldKey()));
        customise(columnHeaderBand,List.of("osBalColumnKey", "currentDateColumnKey"), columnKeys);
        customise(detailBand,List.of("osBalFieldKey", "currentDateFieldKey"),fieldKeys);
    }

    private void customise(JRBand band, List<String> excludeKeys, List<String> elementKeys){
        List<JRElement> tableHeaderColumns = CustomiserHelper.getBandElementsByKeys2(band, elementKeys);
        int tableHeaderWidth = tableHeaderColumns.stream().mapToInt(JRElement::getWidth).sum();
        List<JRElement> nonMifidTableHeaderColumns = new ArrayList<>(tableHeaderColumns);
        Iterator<JRElement> itr = nonMifidTableHeaderColumns.iterator();
        while(itr.hasNext()) {
            JRElement nextElement = itr.next();
            if (excludeKeys.contains(nextElement.getKey())) {
                itr.remove();
            }
        }
        int nonMifidTableHeaderWidth = nonMifidTableHeaderColumns.stream().mapToInt(JRElement::getWidth).sum();
        double rebalanceFactor = (double) tableHeaderWidth / nonMifidTableHeaderWidth;
        CustomiserHelper.hideElements(excludeKeys, band);
        CustomiserHelper.rebalanceColumn(nonMifidTableHeaderColumns, rebalanceFactor);
    }




}
