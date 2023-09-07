package org.sonarsource.plugins.coupling.web;

import org.sonar.api.web.page.Context;
import org.sonar.api.web.page.Page;
import org.sonar.api.web.page.PageDefinition;

import static org.sonar.api.web.page.Page.Qualifier.SUB_VIEW;
import static org.sonar.api.web.page.Page.Qualifier.VIEW;
import static org.sonar.api.web.page.Page.Qualifier.PROJECT;
import static org.sonar.api.web.page.Page.Qualifier.MODULE;
import static org.sonar.api.web.page.Page.Scope.COMPONENT;

public class MyPluginPageDefinition implements PageDefinition {

  @Override
  public void define(Context context) {
    context
      .addPage(Page.builder("coupling/html_report_page")
        .setScope(COMPONENT)
        .setComponentQualifiers(Page.Qualifier.PROJECT, Page.Qualifier.MODULE)
        .setName("Coupling Report: HTML")
        .setAdmin(false).build())
      .addPage(Page.builder("coupling/svg_report_page")
        .setScope(COMPONENT)
        .setComponentQualifiers(Page.Qualifier.PROJECT, Page.Qualifier.MODULE)
        .setName("Coupling Report: SVG")
        .setAdmin(false).build())
      .addPage(Page.builder("coupling/bubble_chart_page")
        .setScope(COMPONENT)
        .setComponentQualifiers(Page.Qualifier.PROJECT, Page.Qualifier.MODULE)
        .setName("Coupling Report: Bubble Graph")
        .setAdmin(false).build());
  }
}
