package org.sonarsource.plugins.coupling.utils;

public class ReportParserException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ReportParserException(String msg) {
        super(msg);
    }
    
    public ReportParserException(String msg, Throwable t) {
        super(msg, t);
    }
}
