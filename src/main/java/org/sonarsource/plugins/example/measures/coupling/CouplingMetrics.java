/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonarsource.plugins.example.measures.coupling;

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
  private static final String REPORT_KEY = "report";
  
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
  public static final Metric<String> REPORT = new Metric.Builder(REPORT_KEY, "Dependency-Check Report", Metric.ValueType.DATA)
  .setDescription("Report HTML")
  .setQualitative(Boolean.FALSE)
  .setDomain(CouplingMetrics.DOMAIN)
  .setHidden(false)
  .setDeleteHistoricalData(true)
  .create();


  @Override
  public List<Metric> getMetrics() {
    return asList(AFFERENT_COUPLING, EFFERENT_COUPLING, INSTABILITY, INSTABILITY_RATING, REPORT);
  }
}
