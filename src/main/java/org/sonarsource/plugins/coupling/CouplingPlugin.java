package org.sonarsource.plugins.coupling;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.coupling.hooks.PostJobInScanner;
import org.sonarsource.plugins.coupling.hooks.DisplayQualityGateStatus;
import org.sonarsource.plugins.coupling.web.MyPluginPageDefinition;
import org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics;
import org.sonarsource.plugins.coupling.measures.coupling.CouplingMetricSensor;
import org.sonarsource.plugins.coupling.measures.coupling.ComputeCaSum;
import org.sonarsource.plugins.coupling.measures.coupling.ComputeCeSum;
import org.sonarsource.plugins.coupling.measures.coupling.ComputeInstabilityAverage;
import org.sonarsource.plugins.coupling.measures.coupling.ComputeInstabilityRating;
/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class CouplingPlugin implements Plugin {

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
