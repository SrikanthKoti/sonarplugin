package org.sonarsource.plugins.coupling.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonarsource.plugins.coupling.base.CouplingPluginConstants;

public class HtmlReportFile extends ReportFile {

    public static HtmlReportFile getHtmlReport(Configuration config, FileSystem fileSystem, PathResolver pathResolver) throws FileNotFoundException {
        String path = config.get(CouplingPluginConstants.HTML_REPORT_PATH_PROPERTY).orElse(CouplingPluginConstants.HTML_REPORT_PATH_DEFAULT);
        File report = pathResolver.relativeFile(fileSystem.baseDir(), path);
        report = checkReport(report, ReportFormat.HTML, CouplingPluginConstants.HTML_REPORT_PATH_PROPERTY);
        if (report == null) {
            throw new FileNotFoundException("HTML-Dependency-Check report does not exist.");
        }
        return new HtmlReportFile(report);
    }

    private HtmlReportFile(File report) {
        super(ReportFormat.HTML, CouplingPluginConstants.HTML_REPORT_PATH_PROPERTY, report);
    }
}
