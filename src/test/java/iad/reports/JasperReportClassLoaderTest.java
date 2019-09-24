package iad.reports;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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
    Assert.assertThat(
        url.toString(), CoreMatchers.is("inmemory:TestReport1/TestReport1_ForTestPurposes.jasper"));
  }

  @Test @SneakyThrows
  public void testShouldGetUrlResourceAsStream() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    Assert.assertNotNull(url.openStream());
  }

  @Test
  public void testShouldGetResourceAsStream() {
    InputStream inputStream = jasperReportClassLoader.getResourceAsStream(RESOURCE);
    Assert.assertNotNull(inputStream);
  }

  @Test
  public void testShouldGetUrlProtocol() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    Assert.assertThat(url.getProtocol(), CoreMatchers.is("inmemory"));
  }

  @Test
  public void testShouldGetUrlHost() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    Assert.assertThat(url.getHost(), CoreMatchers.is(""));
  }

  @Test
  public void testShouldGetUrlPortNumber() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    Assert.assertThat(url.getPort(), CoreMatchers.is(-1));
  }

  @Test
  public void testShouldGetUrlPath() {
    URL url = jasperReportClassLoader.findResource(RESOURCE);
    Assert.assertThat(url.getPath(), CoreMatchers.is(RESOURCE));
  }

  @Test
  public void testShouldFindResourceAsUrlNotSupported() {
    Assert.assertNull(jasperReportClassLoader.findResource(UNSUPPORTED_RESOURCE));
  }
}
