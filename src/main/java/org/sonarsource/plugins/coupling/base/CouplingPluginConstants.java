/*
 * Dependency-Check Plugin for SonarQube
 * Copyright (C) 2015-2023 dependency-check
 * philipp.dallig@gmail.com
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
package org.sonarsource.plugins.coupling.base;

public final class CouplingPluginConstants {

    public static final String JSON_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.jsonReportPath";
    public static final String HTML_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.htmlReportPath";
    public static final String SVG_REPORT_PATH_PROPERTY = "sonar.couplingPlugin.svgReportPath";

    public static final String JSON_REPORT_PATH_DEFAULT = "dependencies.json";
    public static final String HTML_REPORT_PATH_DEFAULT = "dependencies.html";
    public static final String SVG_REPORT_PATH_DEFAULT = "dependencies.svg";


    private CouplingPluginConstants() {}

}
