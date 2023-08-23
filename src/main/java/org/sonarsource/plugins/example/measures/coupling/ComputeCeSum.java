package org.sonarsource.plugins.example.measures.coupling;

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.EFFERENT_COUPLING;

public class ComputeCeSum implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return def.newDefinitionBuilder()
      .setOutputMetrics(EFFERENT_COUPLING.key())
      .build();
  }

  @Override
  public void compute(MeasureComputerContext context) {
    // measure is already defined on files by {@link CouplingMetricSensor}
    // in scanner stack
    if (context.getComponent().getType() != Component.Type.FILE) {
      int sum = 0;
      int count = 0;
      for (Measure child : context.getChildrenMeasures(EFFERENT_COUPLING.key())) {
        sum += child.getIntValue();
        count++;
      }
      // int average = count == 0 ? 0 : sum / count;
      // context.addMeasure(AFFERENT_COUPLING.key(), average);
      context.addMeasure(EFFERENT_COUPLING.key(), sum);
    }
  }
}
