package org.sonarsource.plugins.example.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Module {
    private final String source;
    private final List<Dependency> dependencies;
    private final List<String> dependents;
    private final boolean orphan;
    private final double instability;
    private final boolean valid;
    private final boolean followable; // Add this field

    @JsonCreator
    public Module(
            @JsonProperty(value = "source", required = true) String source,
            @JsonProperty(value = "dependencies") List<Dependency> dependencies,
            @JsonProperty(value = "dependents") List<String> dependents,
            @JsonProperty(value = "orphan") boolean orphan,
            @JsonProperty(value = "instability") double instability,
            @JsonProperty(value = "valid") boolean valid,
            @JsonProperty(value = "followable") boolean followable) {
        this.source = source;
        this.dependencies = dependencies;
        this.dependents = dependents;
        this.orphan = orphan;
        this.instability = instability;
        this.valid = valid;
        this.followable = followable; // Initialize the field

    }

    public String getSource() {
        return source;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<String> getDependents() {
        return dependents;
    }

    public boolean isOrphan() {
        return orphan;
    }

    public double getInstability() {
        return instability;
    }

    public boolean isValid() {
        return valid;
    }
}
