package org.sonarsource.plugins.coupling.measures.coupling;

import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.coupling.utils.*;
import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.AFFERENT_COUPLING;
import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.EFFERENT_COUPLING;
import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.INSTABILITY;
import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.HTML_REPORT;
import static org.sonarsource.plugins.coupling.measures.coupling.CouplingMetrics.SVG_REPORT;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonarsource.plugins.coupling.utils.Module;
import org.sonarsource.plugins.coupling.utils.HtmlReportFile;


import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.sonar.api.scan.filesystem.PathResolver;
import edu.umd.cs.findbugs.annotations.Nullable;
/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */
public class CouplingMetricSensor implements Sensor {
  private final FileSystem fileSystem;
  private final PathResolver pathResolver;
  private final AnalysisWarnings analysisWarnings;


  public CouplingMetricSensor(FileSystem fileSystem, PathResolver pathResolver, @Nullable AnalysisWarnings analysisWarnings) {
    this.fileSystem = fileSystem;
    this.pathResolver = pathResolver;
    this.analysisWarnings = analysisWarnings;
}

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Compute coupling of files");
  }
  private static final Logger LOGGER = Loggers.get(CouplingMetricSensor.class);


public static Optional<Module> findModuleDataBySource(List<Module> moduleList, String source) {
  return moduleList.stream()
          .filter(module -> module.getSource().equals(source))
          .findFirst();
}
  private Optional<Analysis> parseAnalysis(SensorContext context) {
    LOGGER.info("Using JSON-Reportparser");
    try {
        JsonReportFile report = JsonReportFile.getJsonReport(context.config(), fileSystem, pathResolver);
        return Optional.of(JsonReportParserHelper.parse(report.getInputStream()));
    } catch (FileNotFoundException e) {
        LOGGER.info("JSON-Analysis skipped/aborted due to missing report file");
        LOGGER.info(e.getMessage(), e);
    } catch (ReportParserException e) {
        LOGGER.info("JSON-Analysis aborted");
        LOGGER.info(e.getMessage(), e);
    } catch (IOException e) {
        LOGGER.info("JSON-Analysis aborted due to: IO Errors", e);
    }
    return Optional.empty();
  }
  private void uploadHTMLReport(SensorContext context) {
    try {
        HtmlReportFile htmlReportFile = HtmlReportFile.getHtmlReport(context.config(), fileSystem, pathResolver);
        String htmlReport = htmlReportFile.getReportContent();
        if (htmlReport != null) {
            LOGGER.info("Upload Dependency-Check HTML-Report");
            context.<String>newMeasure().forMetric(HTML_REPORT).on(context.project())
                    .withValue(htmlReport).save();
        }
    } catch (FileNotFoundException e) {
        LOGGER.info(e.getMessage());
        LOGGER.debug(e.getMessage(), e);
    }
}
  private void uploadSvgReport(SensorContext context) {
    try {
        SvgReportFile svgReportFile = SvgReportFile.getSvgReport(context.config(), fileSystem, pathResolver);
        String svgReport = svgReportFile.getReportContent();
        if (svgReport != null) {
            LOGGER.info("Upload Dependency-Check SVG-Report");
            context.<String>newMeasure().forMetric(SVG_REPORT).on(context.project())
                    .withValue(svgReport).save();
        }
    } catch (FileNotFoundException e) {
        LOGGER.info(e.getMessage());
        LOGGER.debug(e.getMessage(), e);
    }
  }

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    // only "main" files, but not "tests"   
    try {
      // Access the value of the custom parameter
      Optional<Analysis> jsonData = parseAnalysis(context);
      LOGGER.info("************************");
      LOGGER.info(jsonData.toString());
      if (jsonData.isPresent()) {
        Integer temp = jsonData.get().getModules().size();
        LOGGER.info(temp.toString());
      }
      LOGGER.info("************************");
      
      Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
      for (InputFile file : files) {
        Integer cAvalue = 0;
        Integer cEvalue = 0;
        Optional<Module> module =  findModuleDataBySource(jsonData.get().getModules(),file.relativePath());
        if(module.isPresent()){
          cAvalue = module.get().getDependents().size();
          cEvalue = module.get().getDependencies().size();
        }
        context.<Integer>newMeasure()
          .forMetric(AFFERENT_COUPLING)
          .on(file)
          .withValue(cAvalue)
          .save();
        context.<Integer>newMeasure()
          .forMetric(EFFERENT_COUPLING)
          .on(file)
          .withValue(cEvalue)
          .save();
        Double instabilityValue = 0.0;
          if(cAvalue != 0 || cEvalue != 0){
            instabilityValue = cEvalue.doubleValue() / (cAvalue.doubleValue() + cEvalue.doubleValue());
          }
          context.<Double>newMeasure()
          .forMetric(INSTABILITY)
          .on(file)
          .withValue(instabilityValue)
          .save();
      }
      uploadHTMLReport(context);
      uploadSvgReport(context);
  } catch (Exception e) {
      e.printStackTrace();
  }
  }
}
