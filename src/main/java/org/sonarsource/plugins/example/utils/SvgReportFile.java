package org.sonarsource.plugins.example.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;

public class SvgReportFile extends ReportFile {

    public static SvgReportFile getSvgReport(Configuration config, FileSystem fileSystem, PathResolver pathResolver) throws FileNotFoundException {
        String path = config.get("sonar.dependencySvgPath").orElse("dependencies.svg");
        File report = pathResolver.relativeFile(fileSystem.baseDir(), path);
        report = checkReport(report, ReportFormat.SVG, "sonar.dependencySvgPath");
        if (report == null) {
            throw new FileNotFoundException("SVG-Dependency-Check report does not exist.");
        }
        return new SvgReportFile(report);
    }

    private SvgReportFile(File report) {
        super(ReportFormat.HTML, "sonar.dependencySvgPath", report);
    }
}
