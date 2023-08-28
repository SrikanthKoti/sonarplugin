package org.sonarsource.plugins.coupling.measures.coupling;

import java.util.List;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import static java.util.Arrays.asList;

public class CouplingMetrics implements Metrics {
  private static final String DOMAIN = "Measuring Instability";
  private static final String AFFERENT_COUPLING_KEY = "afferent_coupling";
  private static final String EFFERENT_COUPLING_KEY = "efferent_coupling";
  private static final String INSTABILITY_KEY = "instability";
  private static final String INSTABILITY_RATING_KEY = "instability_rating";
  private static final String HTML_REPORT_KEY = "html_report";
  private static final String SVG_REPORT_KEY = "svg_report";
  
  public static final Metric<Integer> AFFERENT_COUPLING = new Metric.Builder(AFFERENT_COUPLING_KEY, "Afferent Coupling", Metric.ValueType.INT)
  .setDescription("Number of dependents")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CouplingMetrics.DOMAIN)
  .create();

  public static final Metric<Integer> EFFERENT_COUPLING = new Metric.Builder(EFFERENT_COUPLING_KEY, "Efferent Coupling", Metric.ValueType.INT)
  .setDescription("Number of dependencies")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CouplingMetrics.DOMAIN)
  .create();

  public static final Metric<Double> INSTABILITY = new Metric.Builder(INSTABILITY_KEY, "Instability", Metric.ValueType.FLOAT)
  .setDescription("Instability = ca / (ce + ca)")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CouplingMetrics.DOMAIN)
  .create();

  public static final Metric<Integer> INSTABILITY_RATING = new Metric.Builder(INSTABILITY_RATING_KEY, "Instability Rating", Metric.ValueType.RATING)
  .setDescription("Rating based on Instability of a file")
  .setDirection(Metric.DIRECTION_BETTER)
  .setQualitative(true)
  .setDomain(CouplingMetrics.DOMAIN)
  .create();
  
  public static final Metric<String> HTML_REPORT = new Metric.Builder(HTML_REPORT_KEY, "Dependency-Check HTML Report", Metric.ValueType.DATA)
  .setDescription("Report HTML")
  .setQualitative(Boolean.FALSE)
  .setDomain(CouplingMetrics.DOMAIN)
  .setHidden(false)
  .setDeleteHistoricalData(true)
  .create();

  public static final Metric<String>  SVG_REPORT = new Metric.Builder(SVG_REPORT_KEY, "Dependency-Check SVG Report", Metric.ValueType.DATA)
  .setDescription("Report SVG")
  .setQualitative(Boolean.FALSE)
  .setDomain(CouplingMetrics.DOMAIN)
  .setHidden(false)
  .setDeleteHistoricalData(true)
  .create();

  @Override
  public List<Metric> getMetrics() {
    return asList(AFFERENT_COUPLING, EFFERENT_COUPLING, INSTABILITY, INSTABILITY_RATING, HTML_REPORT, SVG_REPORT);
  }
}