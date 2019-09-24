package iad.reports;

import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class JasperReportClassLoader extends ClassLoader {

  private final ReportSupplier reportSupplier;
  private static final String URL_PROTOCOL = "inmemory";
  private static final String HOST = "";
  private static final int DEFAULT_PORT_NUMBER = -1;

  public JasperReportClassLoader(ClassLoader parentClassLoader, ReportSupplier reportSupplier) {
    super(parentClassLoader);
    this.reportSupplier = reportSupplier;
  }

  @Override @SneakyThrows
  protected URL findResource(String fileName) {
    if (reportSupplier.isReportFileName(fileName)) {
      return new URL(
          URL_PROTOCOL, HOST, DEFAULT_PORT_NUMBER, fileName, createUrlStreamHandler(fileName));
    } else {
      return null;
    }
  }

  private URLStreamHandler createUrlStreamHandler(String fileName) {
    return new URLStreamHandler() {
      @Override
      protected URLConnection openConnection(URL u) {
        return createUrlConnection(u, fileName);
      }
    };
  }

  private URLConnection createUrlConnection(URL u, String fileName) {
    return new URLConnection(u) {
      @Override
      public void connect() {
        // Do nothing: Not Implemented
      }

      @Override
      public InputStream getInputStream() {
        if (!getURL().getProtocol().equals(URL_PROTOCOL)) {
          throw new IllegalArgumentException("URL protocol mismatch.");
        }
        if (!getURL().getHost().equals(HOST)) {
          throw new IllegalArgumentException("URL host mismatch.");
        }
        if (getURL().getPort() != DEFAULT_PORT_NUMBER) {
          throw new IllegalArgumentException("URL port number mismatch.");
        }
        if (!getURL().getPath().equals(fileName)) {
          throw new IllegalArgumentException("URL path mismatch.");
        }
        byte[] subReportBytes = reportSupplier.getReport(getURL().getPath());
        return new ByteArrayInputStream(subReportBytes);
      }
    };
  }
}
