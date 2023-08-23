package org.sonarsource.plugins.coupling.measures.coupling;

import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;

import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.AFFERENT_COUPLING;

public class ComputeCaSum implements MeasureComputer {

  @Override
  public MeasureComputerDefinition define(MeasureComputerDefinitionContext def) {
    return def.newDefinitionBuilder()
      .setOutputMetrics(AFFERENT_COUPLING.key())
      .build();
  }

  @Override
  public void compute(MeasureComputerContext context) {
    // measure is already defined on files by {@link CouplingMetricSensor}
    // in scanner stack
    if (context.getComponent().getType() != Component.Type.FILE) {
      int sum = 0;
      int count = 0;
      for (Measure child : context.getChildrenMeasures(AFFERENT_COUPLING.key())) {
        sum += child.getIntValue();
        count++;
      }
      // int average = count == 0 ? 0 : sum / count;
      // context.addMeasure(AFFERENT_COUPLING.key(), average);
      context.addMeasure(AFFERENT_COUPLING.key(), sum);
    }
  }
}
