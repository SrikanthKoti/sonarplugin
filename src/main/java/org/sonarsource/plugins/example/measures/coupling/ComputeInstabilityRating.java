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

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.INSTABILITY;
import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.INSTABILITY_RATING;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class ComputeInstabilityRating implements MeasureComputer {
  private static final int RATING_A = 1;
  private static final int RATING_B = 2;
  private static final int RATING_C = 3;
  private static final Logger LOGGER = Loggers.get(ComputeInstabilityRating.class);

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return def.newDefinitionBuilder()
      .setInputMetrics(INSTABILITY.key())
      .setOutputMetrics(INSTABILITY_RATING.key())
      .build();
  }
  
  @Override
  public void compute(MeasureComputerContext context) {
    // measure is already defined on files by {@link SetSizeOnFilesSensor}
    // in scanner stack
    Measure instability = context.getMeasure(INSTABILITY.key());
    if (instability != null) {
      // rating values are currently implemented as integers in API
      Integer rating = RATING_A;
      if (instability.getDoubleValue() >= 0.4 && instability.getDoubleValue() <= 0.7) {
        rating = RATING_B;
      }
      if (instability.getDoubleValue() >= 0.7 && instability.getDoubleValue() <= 1) {
        rating = RATING_C;
      }
      context.addMeasure(INSTABILITY_RATING.key(), rating);
    }
  }
}
