package iad.reports;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

public class JasperReportClassLoaderTest {

  private JasperReportClassLoader jasperReportClassLoader;
  private static final String UNSUPPORTED_RESOURCE = "TestReport1/unsupported_resource.txt";
  private static final String RESOURCE = "TestReport1/TestReport1_ForTestPurposes.jasper";

  @Before
  public void beforeJasperReportClassLoaderTest() {
    ReportSupplier reportSupplier =
        new JasperReportSupplier<>(null, null, List.of("TestReport1", "TestReportCommon"));
    ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
    jasperReportClassLoader = new JasperReportClassLoader(parentClassLoader, reportSupplier);
  }

  @Test
  public void testShouldFindResourceUrl() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertThat(url.toString(), is("inmemory:TestReport1/TestReport1_ForTestPurposes.jasper"));
  }

  @Test @SneakyThrows
  public void testShouldGetUrlResourceAsStream() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertNotNull(url.openStream());
  }

  @Test
  public void testShouldGetResourceAsStream() {
    InputStream inputStream = jasperReportClassLoader.getResourceAsStream(RESOURCE);
    assertNotNull(inputStream);
  }

  @Test
  public void testShouldGetUrlProtocol() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertThat(url.getProtocol(), is("inmemory"));
  }

  @Test
  public void testShouldGetUrlHost() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertThat(url.getHost(), is(""));
  }

  @Test
  public void testShouldGetUrlPortNumber() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertThat(url.getPort(), is(-1));
  }

  @Test
  public void testShouldGetUrlPath() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    assertThat(url.getPath(), is(RESOURCE));
  }

  @Test
  public void testShouldFindResourceAsUrlNotSupported() {
    assertNull(jasperReportClassLoader.findResource(UNSUPPORTED_RESOURCE));
  }
}
