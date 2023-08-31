import React from "react";
import DependencyCheckReportApp from "./components/DependencyCheckReportApp";
import "../style.css";

// This creates a page for dependencycheck, which shows a html report

//  You can access it at /project/extension/coupling/html_report_page?id={PORTFOLIO_ID}&qualifier=VW
window.registerExtension("coupling/html_report_page", (options) => {
  return <DependencyCheckReportApp options={options} reportType={"html_report"}/>;
});
