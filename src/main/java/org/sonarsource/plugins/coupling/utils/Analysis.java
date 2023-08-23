package org.sonarsource.plugins.coupling.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Analysis {
    private final List<Module> modules;

    @JsonCreator
    public Analysis(@JsonProperty(value = "modules") List<Module> modules) {
        this.modules = modules;
    }

    public List<Module> getModules() {
        return modules;
    }
}
