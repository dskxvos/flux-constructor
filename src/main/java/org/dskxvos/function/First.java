package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;

public class First implements Function{

    private StringBuilder fluxStatement;


    public First(String column) {
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.first+"("+FiledConstants.column+": \""+column+"\")");
    }


    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }
}
