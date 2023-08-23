package org.sonarsource.plugins.coupling.measures.coupling;

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.INSTABILITY;

public class ComputeInstabilityAverage implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return def.newDefinitionBuilder()
      .setOutputMetrics(INSTABILITY.key())
      .build();
  }

  @Override
  public void compute(MeasureComputerContext context) {
    // measure is already defined on files by {@link CouplingMetricSensor}
    // in scanner stack
    if (context.getComponent().getType() != Component.Type.FILE) {
      Double sum = 0.0;
      int count = 0;
      for (Measure child : context.getChildrenMeasures(INSTABILITY.key())) {
        sum += child.getDoubleValue();
        count++;
      }
      Double average = count == 0 ? 0.0 : sum / count;
      context.addMeasure(INSTABILITY.key(), average);
    }
  }
}
