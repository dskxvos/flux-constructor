package org.dskxvos.function;

import org.dskxvos.constructor.FieldConstants;

public class First implements Function{

    private StringBuilder fluxStatement;


    public First(String column) {
        if (null == column || "".equals(column.trim())){
            throw new RuntimeException("column cannot be empty");
        }
        fluxStatement = new StringBuilder(FieldConstants.pipeOperator+ FieldConstants.first+"("+ FieldConstants.column+": \""+column+"\")");
    }


    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }
}
