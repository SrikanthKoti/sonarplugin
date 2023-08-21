package org.sonarsource.plugins.example.models;

import java.util.List;

public class DependencyInfo {
    private String module;
    private String moduleSystem;
    private boolean dynamic;
    private boolean exoticallyRequired;
    private String resolved;
    private boolean coreModule;
    private boolean followable;
    private boolean couldNotResolve;
    private List<String> dependencyTypes;
    private boolean matchesDoNotFollow;
    private boolean circular;
    private int instability;
    private boolean valid;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleSystem() {
        return moduleSystem;
    }

    public void setModuleSystem(String moduleSystem) {
        this.moduleSystem = moduleSystem;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isExoticallyRequired() {
        return exoticallyRequired;
    }

    public void setExoticallyRequired(boolean exoticallyRequired) {
        this.exoticallyRequired = exoticallyRequired;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public boolean isCoreModule() {
        return coreModule;
    }

    public void setCoreModule(boolean coreModule) {
        this.coreModule = coreModule;
    }

    public boolean isFollowable() {
        return followable;
    }

    public void setFollowable(boolean followable) {
        this.followable = followable;
    }

    public boolean isCouldNotResolve() {
        return couldNotResolve;
    }

    public void setCouldNotResolve(boolean couldNotResolve) {
        this.couldNotResolve = couldNotResolve;
    }

    public List<String> getDependencyTypes() {
        return dependencyTypes;
    }

    public void setDependencyTypes(List<String> dependencyTypes) {
        this.dependencyTypes = dependencyTypes;
    }

    public boolean isMatchesDoNotFollow() {
        return matchesDoNotFollow;
    }

    public void setMatchesDoNotFollow(boolean matchesDoNotFollow) {
        this.matchesDoNotFollow = matchesDoNotFollow;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public int getInstability() {
        return instability;
    }

    public void setInstability(int instability) {
        this.instability = instability;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
