package org.sonarsource.plugins.coupling.base;

public final class CouplingPluginConstants {

    public static final String JSON_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.jsonReportPath";
    public static final String HTML_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.htmlReportPath";
    public static final String SVG_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.svgReportPath";

    public static final String SKIP_PROPERTY = "sonar.couplingPlugin.skip";

    public static final String JSON_REPORT_PATH_DEFAULT = "dependencies.json";
    public static final String HTML_REPORT_PATH_DEFAULT = "dependencies.html";
    public static final String SVG_REPORT_PATH_DEFAULT = "dependencies.svg";
    public static final Boolean SKIP_PROPERTY_DEFAULT = Boolean.FALSE;


    private CouplingPluginConstants() {}

}
