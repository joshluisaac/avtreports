package iad.transact.reports.testreport;

import iad.reports.ColumnFieldPair;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRCommonElement;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JasperDesign;

import java.util.*;
import java.util.stream.IntStream;

public class TestReportCustomiser implements Customiser<TestReportFakeData> {

    private static final List<ColumnFieldPair> ALL_COLUMN_HEADER_KEYS =
            List.of(new ColumnFieldPair("nameColumnKey","nameFieldKey")
                    ,new ColumnFieldPair("emailColumnKey","emailFieldKey")
                    ,new ColumnFieldPair("osBalColumnKey","osBalFieldKey")
                    ,new ColumnFieldPair("currentDateColumnKey","currentDateFieldKey")
                    ,new ColumnFieldPair("phoneNumberColumnKey","phoneNumberFieldKey")
    );


    @Override
    public void customise(TestReportFakeData data, JasperDesign design) {
        List<String> excludeKeys = List.of("osBalColumnKey", "currentDateColumnKey");
        JRBand columnHeaderBand = design.getColumnHeader();
        List<String> columnKeys = new ArrayList<>();
        ALL_COLUMN_HEADER_KEYS.forEach(key -> columnKeys.add(key.getColumnKey()));


        List<JRElement> tableHeaderColumns = CustomiserHelper.getBandElementsByKeys2(columnHeaderBand, columnKeys);
        System.out.println(tableHeaderColumns.size());
        int tableHeaderWidth = tableHeaderColumns.stream().mapToInt(JRElement::getWidth).sum();
        System.out.println(tableHeaderWidth);

        List<JRElement> nonMifidTableHeaderColumns = new ArrayList<>(tableHeaderColumns);
        
        IntStream.range(0,nonMifidTableHeaderColumns.size()).forEach(index -> {

            if (excludeKeys.contains(nonMifidTableHeaderColumns.get(index).getKey())) {

                nonMifidTableHeaderColumns.remove(index);
                System.out.println(index);
            }
        });

        System.out.println(nonMifidTableHeaderColumns.size());

    }

    public void run(){

    }


    public static void main(String[] args) {
        new TestReportCustomiser().customise(null,null);
    }
}
