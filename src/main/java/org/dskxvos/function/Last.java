package org.dskxvos.function;

import org.dskxvos.constructor.FieldConstants;

public class Last implements Function{

    private StringBuilder fluxStatement;

    public Last(String column) {
        fluxStatement = new StringBuilder(FieldConstants.pipeOperator+ FieldConstants.last+"("+ FieldConstants.column+":\""+column+"\")");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }
}
