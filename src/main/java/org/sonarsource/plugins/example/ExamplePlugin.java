package org.sonarsource.plugins.example;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.example.hooks.PostJobInScanner;
import org.sonarsource.plugins.example.hooks.DisplayQualityGateStatus;
import org.sonarsource.plugins.example.web.MyPluginPageDefinition;
import org.sonarsource.plugins.example.measures.coupling.CouplingMetrics;
import org.sonarsource.plugins.example.measures.coupling.CouplingMetricSensor;
import org.sonarsource.plugins.example.measures.coupling.ComputeCaSum;
import org.sonarsource.plugins.example.measures.coupling.ComputeCeSum;
import org.sonarsource.plugins.example.measures.coupling.ComputeInstabilityAverage;
import org.sonarsource.plugins.example.measures.coupling.ComputeInstabilityRating;
/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class ExamplePlugin implements Plugin {

  @Override
  public void define(Context context) {
    // tutorial on hooks
    context.addExtensions(PostJobInScanner.class, DisplayQualityGateStatus.class);

    // tutorial on measures
    context
      .addExtensions(CouplingMetrics.class, CouplingMetricSensor.class, ComputeCaSum.class, ComputeCeSum.class, ComputeInstabilityAverage.class, ComputeInstabilityRating.class);

    // tutorial on web extensions
    context.addExtension(MyPluginPageDefinition.class);
  }
}
