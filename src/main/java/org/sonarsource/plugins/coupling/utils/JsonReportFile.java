package org.sonarsource.plugins.coupling.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonarsource.plugins.coupling.base.CouplingPluginConstants;


public class JsonReportFile extends ReportFile {

    public static JsonReportFile getJsonReport(Configuration config, FileSystem fileSystem, PathResolver pathResolver) throws FileNotFoundException {
        String path = config.get(CouplingPluginConstants.JSON_REPORT_PATH_PROPERTY).orElse(CouplingPluginConstants.JSON_REPORT_PATH_DEFAULT);
        File report = pathResolver.relativeFile(fileSystem.baseDir(), path);
        report = checkReport(report, ReportFormat.JSON, CouplingPluginConstants.JSON_REPORT_PATH_PROPERTY);
        if (report == null) {
            throw new FileNotFoundException("JSON-Dependency-Check report does not exist.");
        }
        return new JsonReportFile(report);
    }

    private JsonReportFile(File report) {
        super(ReportFormat.JSON, CouplingPluginConstants.JSON_REPORT_PATH_PROPERTY, report);
    }

}
