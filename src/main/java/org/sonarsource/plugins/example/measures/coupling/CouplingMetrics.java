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

  public static final Metric<Integer> AFFERENT_COUPLING = new Metric.Builder("afferent_coupling", "Afferent Coupling", Metric.ValueType.INT)
  .setDescription("Number of dependents")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CoreMetrics.DOMAIN_COMPLEXITY)
  .create();

public static final Metric<Integer> EFFERENT_COUPLING = new Metric.Builder("efferent_coupling", "Efferent Coupling", Metric.ValueType.INT)
  .setDescription("Number of dependencies")
  .setDirection(Metric.DIRECTION_NONE)
  .setQualitative(false)
  .setDomain(CoreMetrics.DOMAIN_COMPLEXITY)
  .create();
public static final Metric<Integer> INSTABILITY = new Metric.Builder("instability", "Instability", Metric.ValueType.FLOAT)
  .setDescription("Instability = Ca / (Ce + Ca)")
  .setDirection(Metric.DIRECTION_BETTER)
  .setQualitative(false)
  .setDomain(CoreMetrics.DOMAIN_COMPLEXITY)
  .create();
  @Override
  public List<Metric> getMetrics() {
    return asList(AFFERENT_COUPLING, EFFERENT_COUPLING);
  }
}
