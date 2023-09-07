import React from "react";
import { getJSON } from "sonar-request";
import { Bubble } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  LinearScale,
  PointElement,
  Tooltip,
  Legend,
} from 'chart.js';
import zoomPlugin from 'chartjs-plugin-zoom';

export function isBranch(branchLike) {
  return branchLike !== undefined && branchLike.isMain !== undefined;
}

export function isPullRequest(branchLike) {
  return branchLike !== undefined && branchLike.key !== undefined;
}

export function findDependencyCheckReport(options, reportType) {
  var request = {
    component: options.component.key,
    metricKeys: reportType
  };

  // branch and pullRequest are internal parameters for /api/measures/component
  if (isBranch(options.branchLike)) {
    request.branch = options.branchLike.name;
  } else if (isPullRequest(options.branchLike)) {
    request.pullRequest = options.branchLike.key;
  }
  return getJSON("/api/measures/component", request).then(function (response) {
    var report = response.component.measures.find((measure) => {
      return measure.metric === reportType;
    });
    if (typeof report === "undefined") {
      return `<center><h2>No JSON-Report found. Please check property sonar.couplingPlugin.jsonReportPath</h2></center>`;
    } else {
      return report.value;
    }
  });
}
ChartJS.register(LinearScale, PointElement, Tooltip, Legend, zoomPlugin);

export default class BubbleGraph extends React.PureComponent {
  constructor() {
    super();
    this.chartRef = React.createRef();
    this.state = {
      loading: true,
      data: "",
      height: 0,
    };
  }


  componentDidMount() {

    findDependencyCheckReport(this.props.options, 'json_report').then((data) => {
      function getLastPathComponent(path) {
        const pathComponents = path.split('/');
        return pathComponents[pathComponents.length - 1];
      }
      const newData = JSON.parse(data);
      const ratingC = []
      const ratingA = []
      const ratingB = []
      newData.modules.forEach(obj => {
        const ca = obj.dependents.length;
        const ce = obj.dependencies.length;
        const I = (obj.dependencies.length / (obj.dependencies.length + obj.dependents.length));
        const dataObj = {
          x: ce,
          y: ca,
          z: I,
          r: I === 0 ? 2 : 20 * I, // Dynamic radius
          ca,
          ce,
          source: getLastPathComponent(obj.source),
        }
        if (I >= 0 && I < 0.4) {
          ratingA.push(dataObj)
        }
        else if (I >= 0.4 && I < 0.7) {
          ratingB.push(dataObj)
        } else if (I >= 0.7 && I <= 1) {
          ratingC.push(dataObj)
        }


      });
      const zoomOptions = {
        pan: {
          enabled: true,
          mode: 'xy',
        },
        zoom: {
          wheel: {
            enabled: true,
          },
          pinch: {
            enabled: true
          },
          mode: 'xy',
        }
      };
      const chartData = {
        0: ratingA,
        1: ratingB,
        2: ratingC,
      }
      const options = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            title: {
              display: true,
              text: 'Efferent Coupling',
            },
          },
          y: {
            title: {
              display: true,
              text: 'Afferent Coupling',
            },
          },
        },
        plugins: {
          zoom: zoomOptions,
          tooltip: {
            callbacks: {
              label: (context) => {
                const dataPoint = chartData[context.datasetIndex][context.dataIndex];
                const ca = dataPoint.ca.toFixed(2);
                const ce = dataPoint.ce.toFixed(2);
                const I = dataPoint.x.toFixed(2);
                const fileName = dataPoint.source;
                return `Source:${fileName} Instability: ${I}, Ca: ${ca}, Ce: ${ce}`;
              },
            },
          },
        },

      };


      this.setState({
        loading: false,
        data: {
          datasets: [
            {
              label: 'Instability Rating A',
              data: ratingA,
              backgroundColor: 'rgba(22, 237, 22, 0.5)',
            },
            {
              label: 'Instability Rating B',
              data: ratingB,
              backgroundColor: 'rgba(161, 251, 54, 0.52)',
            },
            {
              label: 'Instability Rating C',
              data: ratingC,
              backgroundColor: 'rgba(248, 187, 64, 0.52)',
            }
          ],
        },
        options
      });
    });
    this.updateDimensions();
    window.addEventListener("resize", this.updateDimensions.bind(this));
  }

  componentWillUnmount() {
    window.removeEventListener("resize", this.updateDimensions.bind(this));
  }

  updateDimensions() {
    let updateHeight = window.innerHeight - (72 + 48 + 145.5);
    this.setState({ height: updateHeight });
  }
  resetZoom = () => {
    const chart = this.chartRef.current;
    chart.resetZoom();
  };

  render() {
    const chartContainerStyle = {
      height: '500px',
      paddingLeft: '20px',
      paddingRight: '20px',
    };
    if (this.state.loading) {
      return (
        <div className="page page-limited">
          Loading...
        </div>
      );
    }
    return <div style={chartContainerStyle}>
      <Bubble
        ref={this.chartRef}
        options={this.state.options}
        data={this.state.data}
      />
      <button onClick={this.resetZoom}>Reset Zoom</button>

    </div>;
  }
}
