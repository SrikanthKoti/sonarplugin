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

- **Afferent Coupling (Ca)**
    - Number of incoming dependencies, i.e.. number of other modules that depend on a particular class/module.
    - Basically, Ca counts how many external entities reference or use a given module.
    - High Afferent Coupling is Bad. While it’s not inherently bad, it can lead to challenges in understanding and maintaining the code, especially if changes to the module impact many other parts of the system.

- **Efferent Coupling (Ce)**
    - Number of outgoing dependencies. i.e.. number of other modules or classes that a particular module or class depends on.
    - Ce quantifies how many external entities are used or referenced by the module being measured
    - High efferent coupling can indicate that a module is highly dependent on external functionality, potentially making it less independent and more tightly coupled with other parts of the system. This can lead to challenges when making changes to the module or when trying to reuse it in other contexts.

- **Instability (I)**
    - It is a software design metric that measures the balance between Afferent Coupling (Ca) and Efferent Coupling (Ce) for a module or class.

        ```
        I = Ce / (Ca + Ce) 
        Where 0 ≤ I ≤ 1    
        ```
    - High Instability (Unstable): When I is close to 1, the module is unstable and prone to changes. It suggests that the module depends heavily on external entities, making it less reliable and more susceptible to changes in its dependencies.
    - The goal is to achieve a balance between incoming and outgoing dependencies, leading to a more stable module (lower I value).
    - Aim to reduce Ce more significantly than Ca, as this will directly decrease the numerator of the instability metric (I).

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
    - `-Dsonar.couplingPlugin.jsonReportPath=functions/dependencies.json`
    - `-Dsonar.couplingPlugin.htmlReportPath=functions/dependencies.html`
    - `-Dsonar.couplingPlugin.svgReportPath=functions/dependencies.svg`

## Note
If you an error for java heep memory issue then you need to add size and memory to the sonar analysis.
Under the sonar analysis step, give `size: 1x` & under docker give `memory: 2048`

For reference you can check out [db-diagram](https://bitbucket.org/peoppl_co/db-diagram/src/sonarPluginTest/bitbucket-pipelines.yml) repo.


[dependency-cruiser]: https://www.npmjs.com/package/dependency-cruiser
[sonar-custom-plugin-example]: https://github.com/SonarSource/sonar-custom-plugin-example