import React from "react";
import DependencyCheckReportApp from "../html_report_page/components/DependencyCheckReportApp";
import "../style.css";

// This creates a page for dependencycheck, which shows a html report

//  You can access it at /project/extension/coupling/svg_report_page?id={PORTFOLIO_ID}&qualifier=VW
window.registerExtension("coupling/svg_report_page", (options) => {
  return <DependencyCheckReportApp options={options} reportType={"svg_report"}/>;
});
