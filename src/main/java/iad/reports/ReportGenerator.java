package iad.reports;

import java.io.InputStream;
import java.io.OutputStream;

public interface ReportGenerator<T extends ReportData> {
  InputStream generatePdf(T data);

  void generatePdf(T data, OutputStream outputStream);
}
