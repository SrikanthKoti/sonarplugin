package org.sonarsource.plugins.example.web;

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
      .addPage(Page.builder("example/html_report_page")
        .setScope(COMPONENT)
        .setComponentQualifiers(Page.Qualifier.PROJECT, Page.Qualifier.MODULE)
        .setName("Coupling Report: HTML")
        .setAdmin(false).build())
      .addPage(Page.builder("example/svg_report_page")
        .setScope(COMPONENT)
        .setComponentQualifiers(Page.Qualifier.PROJECT, Page.Qualifier.MODULE)
        .setName("Coupling Report: SVG")
        .setAdmin(false).build());
  }
}
