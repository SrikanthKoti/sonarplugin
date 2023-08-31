package org.sonarsource.plugins.coupling.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonarsource.plugins.coupling.base.CouplingPluginConstants;

public class SvgReportFile extends ReportFile {

    public static SvgReportFile getSvgReport(Configuration config, FileSystem fileSystem, PathResolver pathResolver) throws FileNotFoundException {
        String path = config.get(CouplingPluginConstants.SVG_REPORT_PATH_PROPERTY).orElse(CouplingPluginConstants.SVG_REPORT_PATH_DEFAULT);
        File report = pathResolver.relativeFile(fileSystem.baseDir(), path);
        report = checkReport(report, ReportFormat.SVG, CouplingPluginConstants.SVG_REPORT_PATH_PROPERTY);
        if (report == null) {
            throw new FileNotFoundException("SVG-Dependency-Check report does not exist.");
        }
        return new SvgReportFile(report);
    }

    private SvgReportFile(File report) {
        super(ReportFormat.HTML, CouplingPluginConstants.SVG_REPORT_PATH_PROPERTY, report);
    }
}
