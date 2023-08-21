package org.sonarsource.plugins.example.models;

import java.util.List;

public class ModuleInfo {
    private String source;
    private List<DependencyInfo> dependencies;
    private List<String> dependents;
    private boolean orphan;
    private int instability;
    private boolean valid;

    @Override
    public String toString() {
        return "ModuleInfo{" +
                "source='" + source + '\'' +
                ", dependencies=" + dependencies +
                ", dependents=" + dependents +
                ", orphan=" + orphan +
                ", instability=" + instability +
                ", valid=" + valid +
                '}';
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<DependencyInfo> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyInfo> dependencies) {
        this.dependencies = dependencies;
    }

    public List<String> getDependents() {
        return dependents;
    }

    public void setDependents(List<String> dependents) {
        this.dependents = dependents;
    }

    public boolean isOrphan() {
        return orphan;
    }

    public void setOrphan(boolean orphan) {
        this.orphan = orphan;
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
