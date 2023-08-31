package org.sonarsource.plugins.coupling.utils;

import java.io.IOException;
import java.io.InputStream;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReportParserHelper {

    private JsonReportParserHelper() {
        // do nothing
    }

    public static Analysis parse(InputStream inputStream) throws ReportParserException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(inputStream, Analysis.class);
        } catch (JsonParseException e) {
            throw new ReportParserException("Could not parse JSON-Report", e);
        } catch (JsonMappingException e) {
            throw new ReportParserException("Problem with JSON-Report-Mapping", e);
        } catch (IOException e) {
            throw new ReportParserException("IO Problem with JSON-Report", e);
        }
    }
}
