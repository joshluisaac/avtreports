package iad.reports;

import net.sf.jasperreports.engine.design.JasperDesign;

public interface Customiser<T extends ReportData> {

  void customise(T data, JasperDesign design);
}
