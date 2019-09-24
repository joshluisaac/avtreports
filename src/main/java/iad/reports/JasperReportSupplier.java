package iad.reports;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperReportSupplier<T extends ReportData> implements ReportSupplier {

  private Map<String, Customiser<? super T>> customisers;
  private T data;
  private List<String> allowedReportDirs;
  private Map<String, byte[]> cache = new HashMap<>();

  public JasperReportSupplier(
      Map<String, Customiser<? super T>> customisers, T data, List<String> allowedReportDirs) {
    this.customisers = customisers;
    this.data = data;
    this.allowedReportDirs = allowedReportDirs;
    validateCustomisableSubReports();
  }

  private void validateCustomisableSubReports() {
    if (customisers != null) {
      customisers.forEach(
          (reportId, value) -> {
            checkReportAllowed(reportId, allowedReportDirs);
            if (!reportExists(getReportSourcePath(reportId))) {
              throw new IllegalArgumentException(
                  String.format("The requested report (%s) is not a valid path.", reportId));
            }
          });
    }
  }

  @Override
  public byte[] getReport(String reportId) {
    checkReportAllowed(reportId, allowedReportDirs);
    byte[] cachedEntry = cache.get(reportId);
    if (cachedEntry != null) {
      return cachedEntry;
    }
    JasperDesign jasperDesign = getReportSource(reportId);
    customiseReport(reportId, jasperDesign);
    byte[] jr = compileJasperDesign(jasperDesign);
    cache.put(reportId, jr);
    return jr;
  }

  @SneakyThrows
  private JasperDesign getReportSource(String reportId) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(getReportSourcePath(reportId));
    if (inputStream == null) {
      throw new IllegalArgumentException(
          String.format(
              "Please ensure the specified report (%s) resolves to an actual file", reportId));
    }
    return JRXmlLoader.load(inputStream);
  }

  private void customiseReport(String reportId, JasperDesign jasperDesign) {
    if (customisers != null) {
      Customiser<? super T> customiser = customisers.get(reportId);
      if (customiser != null) {
        customiser.customise(data, jasperDesign);
      }
    }
  }

  @SneakyThrows
  private byte[] compileJasperDesign(JasperDesign jasperDesign) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    JasperCompileManager.compileReportToStream(jasperDesign, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  @Override
  public boolean isReportFileName(String fileName) {
    return fileName.endsWith(".jasper");
  }

  private void checkReportAllowed(String reportId, Collection<String> allowedReportDirs) {
    String token = tokenizeReportId(reportId).get(0);
    if (!allowedReportDirs.contains(token)) {
      throw new IllegalArgumentException(
          String.format(
              "The requested report (%s) does not exist within the allowed paths.", reportId));
    }
  }

  private boolean reportExists(String reportId) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(reportId);
    return url != null;
  }

  private String getReportSourcePath(String reportId) {
    return reportId.replaceAll("\\.jasper$", "").concat(".jrxml");
  }

  private List<String> tokenizeReportId(String reportId) {
    return List.of(reportId.split("/"));
  }
}
