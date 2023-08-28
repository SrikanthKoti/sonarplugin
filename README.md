# Coupling Metrics Plugin for SonarQube 10.x.


Integrates [Dependency-Cruiser][] reports into SonarQube v10.x or higher.

## About Dependency-Cruiser

Dependency-Cruiser is an npm package that can validate and visualise dependencies. This runs through the dependencies in any JavaScript, TypeScript, LiveScript or CoffeeScript project and ...
- ... **validates** them against (your own) [rules](https://github.com/sverweij/dependency-cruiser/blob/main/doc/rules-reference.md)
- ... **reports** violated rules
  - in text (for your builds)
  - in graphics (for your eyeballs)

Walk-through
--------
1. [Project Structure](https://drive.google.com/file/d/1pAJFfiexAz0WXvFdXCw97FbZxoelAyuQ/view?usp=drive_link)
2. [Code Read]
3. [Plugin setup in existing pipeline]

## Note

**This SonarQube plugin does not perform analysis**, rather, it reads existing Dependency-Cruiser reports. 
- **JSON** report is used to analize the Afferent coupling, Efferent Couplung & Instability of each file in the project. 
- **HTML** & **SVG** reports are optional, when provided it will add 2 project level web extentions that show these HTML & SVG reports respectively

Use [this](https://levelup.gitconnected.com/brief-introduction-of-dependency-cruiser-7e38a41afa4f) blog to know more about how to generate these reports.

## Metrics

The plugin keeps track of a number of statistics including:

-   Afferent Coupling (Ca)
    - The number of classes outside the package that depend on classes within the module.
-   Efferent Coupling (Ce)
    - The number of classes outside the package that are depended upon by classes within the module.
-   Instability (I)
    - A metric that quantifies the balance between incoming and outgoing dependencies of a package.
    ```
    I = Ce / (Ca + Ce)
    ```
    - Where 0 ≤ I ≤ 1

Additionally, the following two metrics are defined:

### Instability Rating

Shows rating for each file based on the Instablity value. You can also add this to the Quality Gates of your project along with coverage, bugs etc.. Below is how sonar shows the rating.

```java
A -> 0 to 0.4
B -> 0.4 to 0.7
C -> 0.7 to 1
```

## Compiling

> $ mvn clean package

### Working with NodeJS

-   Start SonarQube Server
-   Run `npm start` inside `sonar-dependency-check-plugin`
    -   Adjust `DEFAULT_PORT`, `PROXY_URL`, `PROXY_CONTEXT_PATH` for your environment

## Installation

Copy the plugin (jar file) to $SONAR_INSTALL_DIR/extensions/plugins and restart SonarQube or install via SonarQube Marketplace(Not published yet).

## How to use this plugin to pur existing bitbucket pipeline
### Step 1: Adding a script to generate dependency graph reports
- Install dependency-cruiser in dev dependencies `npm i -D dependency-cruiser`
- Add this `generate-dependency-graph.sh` to your functions folder, this script generated a JSON,HTml & SVG reports & saves it in the functions folder.
    ```
    #!/bin/bash
    
    # Store the current directory
    CURRENT_DIR=$(pwd)
    
    
    # Use locally installed depcruise package
    DEPCRUISE_PATH=$(npm bin)/depcruise
    
    # Navigate to the project directory
    cd ..
    
    # Run the commands in the project directory
    echo "Running commands in the can-engage directory"
    $DEPCRUISE_PATH --exclude "node_modules" --no-config --output-type dot functions | dot -T svg > functions/dependencies.svg
    $DEPCRUISE_PATH --exclude 'node_modules' --output-type html functions > functions/dependencies.html --metrics --no-config
    $DEPCRUISE_PATH --exclude 'node_modules' --output-type json functions > functions/dependencies.json --no-config --metrics
    
    echo "Commands completed."
    
    # Return to the functions directory
    cd functions
    
    ```
- Add this as a script in functions/package.json `"dependencygraph:generate": "./generate-dependency-graph.sh"`

### Step 2: Modifying the bitbucket pipeline.
- under `script`, add `apt install graphviz -y` command to install graphviz. It's needed to generate SVG reports.
- under `artifacts` add `functions/*` to add the generated reports in the functions folder as artifacts.
- add below 3 command line parrams to the sonnar-scanner script
    - `-Dsonar.dependencyJsonPath=functions/dependencies.json`
    - `-Dsonar.dependencyHtmlPath=functions/dependencies.html`
    - `-Dsonar.dependencySvgPath=functions/dependencies.svg`

## Note
If you an error for java heep memory issue then you need to add size and memory to the sonar analysis.
Under the sonar analysis step, give `size: 1x` & under docker give `memory: 2048`

For reference you can check out [db-diagram](https://bitbucket.org/peoppl_co/db-diagram/src/sonarPluginTest/bitbucket-pipelines.yml) repo.


[dependency-cruiser]: https://www.npmjs.com/package/dependency-cruiser
[sonar-custom-plugin-example]: https://github.com/SonarSource/sonar-custom-plugin-example