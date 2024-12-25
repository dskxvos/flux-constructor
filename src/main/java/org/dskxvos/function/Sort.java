package org.dskxvos.function;

import org.dskxvos.constructor.FieldConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class Sort implements Function{

    private StringBuilder fluxStatement;

    public Sort(boolean desc,String... columns) {
        Collection<String> columnsArg = Arrays.asList(columns);
        fluxStatement = new StringBuilder(FieldConstants.pipeOperator+ FieldConstants.sort+"("+ FieldConstants.columns+": ["+ StringUtils.quotationMark(columnsArg) +"],"+ FieldConstants.desc+":"+desc+")");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }

}
