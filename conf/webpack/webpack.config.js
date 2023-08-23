const path = require("path");
const autoprefixer = require("autoprefixer");

module.exports = {
  // Define the entry points here. They MUST have the same name as the page_id
  // defined in src/main/java/org/sonarsource/plugins/coupling/web/MyPluginPageDefinition.java
  entry: {
    
    // Using React:
    html_report_page: ["./src/main/js/html_report_page/index.js"],
    svg_report_page: ["./src/main/js/svg_report_page/index.js"],

  },
  output: {
    // The entry point files MUST be shipped inside the final JAR's static/
    // directory.
    path: path.join(__dirname, "../../target/classes/static"),
    filename: "[name].js"
  },
  resolve: {
    root: path.join(__dirname, "src/main/js")
  },
  externals: {
    // React 16.8 ships with SonarQube, and should be re-used to avoid 
    // collisions at runtime.
    react: "React",
    "react-dom": "ReactDOM",
    // Register the Sonar* globals as packages, to simplify importing.
    // See src/main/js/common/api.js for more information on what is exposed 
    // in SonarRequest.
    "sonar-request": "SonarRequest",
  },
  module: {
    // Our example uses Babel to transpile our code.
    loaders: [
      {
        test: /\.js$/,
        loader: "babel",
        exclude: /(node_modules)/
      },
      {
        test: /\.css/,
        loader: "style-loader!css-loader!postcss-loader"
      },
      { test: /\.json$/, loader: "json" }
    ]
  },
  postcss() {
    return [
      autoprefixer({
        browsers: [
          "last 3 Chrome versions",
          "last 3 Firefox versions",
          "last 3 Safari versions",
          "last 3 Edge versions",
          "IE 11"
        ]
      })
    ];
  }
};
