package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;

public class Last implements Function{

    private StringBuilder fluxStatement;

    public Last(String column) {
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.last+"("+FiledConstants.column+":"+column+")");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }
}
