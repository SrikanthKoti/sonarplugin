import React from "react";
import DependencyCheckReportApp from "../html_report_page/components/DependencyCheckReportApp";
import "../style.css";
import BubbleGraph from "./components/bubble_graph";

// This creates a page for dependencycheck, which shows a html report

//  You can access it at /project/extension/coupling/bubble_chart_page?id={PORTFOLIO_ID}&qualifier=VW
window.registerExtension("coupling/bubble_chart_page", (options) => {
  return <BubbleGraph options={options} />;
});
