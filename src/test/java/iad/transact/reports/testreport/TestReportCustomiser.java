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

    private static final List<String> MIFID_COLUMN_KEYS = List.of("osBalColumnKey", "currentDateColumnKey");
    private static final List<String> MIFID_FIELD_KEYS = List.of("osBalFieldKey", "currentDateFieldKey");


    @Override
    public void customise(TestReportFakeData data, JasperDesign design) {
        JRBand columnHeaderBand = design.getColumnHeader();
        JRBand detailBand = design.getDetailSection().getBands()[0];
        List<String> columnKeys = new ArrayList<>();
        COLUMN_FIELD_PAIRS.forEach(key -> columnKeys.add(key.getColumnKey()));
        List<String> fieldKeys = new ArrayList<>();
        COLUMN_FIELD_PAIRS.forEach(key -> fieldKeys.add(key.getFieldKey()));

        //List<JRElement> bandElements = CustomiserHelper.getBandElementsByKeys(columnHeaderBand, columnKeys);
        //int tableHeaderWidth = bandElements.stream().mapToInt(JRElement::getWidth).sum();
        //List<JRElement> nonMifidTableElements = getNonMifidTableElements(MIFID_COLUMN_KEYS,bandElements);
        //int nonMifidTableHeaderWidth = nonMifidTableElements.stream().mapToInt(JRElement::getWidth).sum();
        //double rebalanceFactor = (double) tableHeaderWidth / nonMifidTableHeaderWidth;
        //CustomiserHelper.hideElements(MIFID_COLUMN_KEYS, columnHeaderBand);
        //CustomiserHelper.updateElement(nonMifidTableElements, rebalanceFactor);


        rebalanceElements(columnHeaderBand,MIFID_COLUMN_KEYS, columnKeys);
        rebalanceElements(detailBand,MIFID_FIELD_KEYS,fieldKeys);
    }

    private void rebalanceElements(JRBand band, List<String> excludeKeys, List<String> elementKeys){
        List<JRElement> bandElements = CustomiserHelper.getBandElementsByKeys(band, elementKeys);
        int tableHeaderWidth = bandElements.stream().mapToInt(JRElement::getWidth).sum();
        List<JRElement> nonMifidTableElements = getNonMifidTableElements(excludeKeys,bandElements);
        int nonMifidTableHeaderWidth = nonMifidTableElements.stream().mapToInt(JRElement::getWidth).sum();
        double rebalanceFactor = (double) tableHeaderWidth / nonMifidTableHeaderWidth;
        CustomiserHelper.hideElements(excludeKeys, band);
        CustomiserHelper.updateElement(nonMifidTableElements, rebalanceFactor);
    }

    private List<JRElement> getNonMifidTableElements(List<String> excludeKeys,List<JRElement> bandElements){
        List<JRElement> nonMifidTableElements = new ArrayList<>(bandElements);
        Iterator<JRElement> itr = nonMifidTableElements.iterator();
        while(itr.hasNext()) {
            JRElement nextElement = itr.next();
            if (excludeKeys.contains(nextElement.getKey())) {
                itr.remove();
            }
        }
        return nonMifidTableElements;
    }




}
