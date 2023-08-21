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

import java.io.FileNotFoundException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.example.utils.*;
import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.AFFERENT_COUPLING;
import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.EFFERENT_COUPLING;
import static org.sonarsource.plugins.example.measures.coupling.CouplingMetrics.INSTABILITY;
import org.sonarsource.plugins.example.models.ModuleInfo;
import org.sonar.api.notifications.AnalysisWarnings;
import org.sonarsource.plugins.example.utils.Module;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
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

  

public static Optional<ModuleInfo> findModuleBySource(List<ModuleInfo> moduleInfoList, String source) {
  return moduleInfoList.stream()
          .filter(moduleInfo -> moduleInfo.getSource().equals(source))
          .findFirst();
}
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
  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
    // only "main" files, but not "tests"   
    try {
      // Access the value of the custom parameter
      // String jsonPath = context.config().get("sonar.dependencyJsonPath").orElse(null);
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
        LOGGER.info("#-------------------------------------------#");
        LOGGER.info(file.absolutePath());
        LOGGER.info(file.relativePath());
        LOGGER.info(file.filename());
        Integer cAvalue = 0;
        Integer cEvalue = 0;
        Optional<Module> module =  findModuleDataBySource(jsonData.get().getModules(),file.relativePath());
        if(module.isPresent()){
          cAvalue = module.get().getDependents().size();
          cEvalue = module.get().getDependencies().size();
          LOGGER.info("Dependents: " + cAvalue);
          LOGGER.info("Dependencies: " + cAvalue);
          LOGGER.info(module.get().toString());
        }
        else {
          LOGGER.info("Dependents: " + "NOT_FOUND");

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
            instabilityValue = cAvalue.doubleValue() / (cAvalue.doubleValue() + cEvalue.doubleValue());
          }
          context.<Double>newMeasure()
          .forMetric(INSTABILITY)
          .on(file)
          .withValue(instabilityValue)
          .save();
          LOGGER.info("Instability: ", instabilityValue);
          LOGGER.info("--                     **                  --");
      }
  } catch (Exception e) {
      e.printStackTrace();
  }
  }
}
