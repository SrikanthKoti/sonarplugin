package org.sonarsource.plugins.example.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dependency {
    private final String module;
    private final String moduleSystem;
    private final boolean dynamic;
    private final boolean exoticallyRequired;
    private final String resolved;
    private final boolean coreModule;
    private final boolean followable;
    private final boolean couldNotResolve;
    private final List<String> dependencyTypes;
    private final boolean matchesDoNotFollow;
    private final boolean circular;
    private final double instability;
    private final boolean valid;

    @JsonCreator
    public Dependency(
            @JsonProperty(value = "module", required = true) String module,
            @JsonProperty(value = "moduleSystem") String moduleSystem,
            @JsonProperty(value = "dynamic") boolean dynamic,
            @JsonProperty(value = "exoticallyRequired") boolean exoticallyRequired,
            @JsonProperty(value = "resolved") String resolved,
            @JsonProperty(value = "coreModule") boolean coreModule,
            @JsonProperty(value = "followable") boolean followable,
            @JsonProperty(value = "couldNotResolve") boolean couldNotResolve,
            @JsonProperty(value = "dependencyTypes") List<String> dependencyTypes,
            @JsonProperty(value = "matchesDoNotFollow") boolean matchesDoNotFollow,
            @JsonProperty(value = "circular") boolean circular,
            @JsonProperty(value = "instability") double instability,
            @JsonProperty(value = "valid") boolean valid) {
        this.module = module;
        this.moduleSystem = moduleSystem;
        this.dynamic = dynamic;
        this.exoticallyRequired = exoticallyRequired;
        this.resolved = resolved;
        this.coreModule = coreModule;
        this.followable = followable;
        this.couldNotResolve = couldNotResolve;
        this.dependencyTypes = dependencyTypes;
        this.matchesDoNotFollow = matchesDoNotFollow;
        this.circular = circular;
        this.instability = instability;
        this.valid = valid;
    }

    public String getModule() {
        return module;
    }

    public String getModuleSystem() {
        return moduleSystem;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public boolean isExoticallyRequired() {
        return exoticallyRequired;
    }

    public String getResolved() {
        return resolved;
    }

    public boolean isCoreModule() {
        return coreModule;
    }

    public boolean isFollowable() {
        return followable;
    }

    public boolean isCouldNotResolve() {
        return couldNotResolve;
    }

    public List<String> getDependencyTypes() {
        return dependencyTypes;
    }

    public boolean isMatchesDoNotFollow() {
        return matchesDoNotFollow;
    }

    public boolean isCircular() {
        return circular;
    }

    public double getInstability() {
        return instability;
    }

    public boolean isValid() {
        return valid;
    }
}
