package iad.transact.reports.testreport;

import iad.reports.ColumnFieldPair;
import iad.reports.Customiser;
import iad.reports.CustomiserHelper;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
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
        hideElements(excludeKeys, columnHeaderBand);
        rebalanceColumn(nonMifidTableHeaderColumns, rebalanceFactor);
    }

    private void rebalanceColumn(List<JRElement> columnElements, double rebalanceFactor) {
        Integer[] totalWidth = { 0 };
    IntStream.range(0, columnElements.size())
        .forEach(
            index -> {
              JRElement column = columnElements.get(index);
              double newWidth = (column.getWidth() * rebalanceFactor);
              if (index == 0) {
                column.setWidth((int) Math.round(newWidth));
                  System.out.println(column.getKey());
              } else {
                column.setX(totalWidth[0]);
                column.setWidth((int) Math.round(newWidth));
              }
              totalWidth[0] = totalWidth[0] + (int) Math.round(newWidth);
            });

/*        columnElements.forEach(
            column -> {
              double newWidth = (column.getWidth() * rebalanceFactor);
                if(column.getKey().equals("nameColumnKey")){
                    column.setWidth((int) Math.round(newWidth));
                }else {
                    column.setX(totalWidth[0]);
                    column.setWidth((int) Math.round(newWidth));
                }
                totalWidth[0] = totalWidth[0] + (int) Math.round(newWidth);
            });*/
    }

    private void hideElements(
            List<String> keys, JRBand band) {
        keys.forEach(
                entry -> CustomiserHelper.hideElementByKey(band, entry));
    }


}
