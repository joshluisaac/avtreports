package iad.reports;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperReportGenerator<T extends ReportData> implements ReportGenerator<T> {

  private String reportName;
  private List<String> allowedReportDirs;
  private Map<String, Customiser<? super T>> customisers;

  public JasperReportGenerator(
      String reportName,
      List<String> allowedReportDirs,
      Map<String, Customiser<? super T>> customisers) {
    this.reportName = reportName;
    this.allowedReportDirs = allowedReportDirs;
    this.customisers = customisers;
  }

  @SneakyThrows
  private JasperPrint getPrint(T data) {
    Map<String, Object> reportMap = new HashMap<>();
    ReportSupplier reportSupplier =
        new JasperReportSupplier<>(customisers, data, allowedReportDirs);
    ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
    reportMap.put("REPORT_DATA", data);
    reportMap.put(
        JRParameter.REPORT_CLASS_LOADER,
        new JasperReportClassLoader(parentClassLoader, reportSupplier));
    byte[] mainReportBytes =
        reportSupplier.getReport(reportName + "/" + reportName.concat("Main.jasper"));
    InputStream inputStream = new ByteArrayInputStream(mainReportBytes);
    return JasperFillManager.fillReport(inputStream, reportMap);
  }

  @Override @SneakyThrows
  public InputStream generatePdf(T data) {
    return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(getPrint(data)));
  }

  @Override @SneakyThrows
  public void generatePdf(T data, OutputStream outputStream) {
    JasperExportManager.exportReportToPdfStream(getPrint(data), outputStream);
  }
}
