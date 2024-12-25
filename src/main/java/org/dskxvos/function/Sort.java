package org.dskxvos.function;

import org.dskxvos.constructor.FieldConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Collection;
import java.util.List;

public class Sort implements Function{

    private StringBuilder fluxStatement;

    public Sort(boolean desc,String... columns) {
        Collection<String> columnsArg = List.of(columns);
        fluxStatement = new StringBuilder(FieldConstants.pipeOperator+ FieldConstants.sort+"("+ FieldConstants.columns+": ["+ StringUtils.quotationMark(columnsArg) +"],"+ FieldConstants.desc+":"+desc+")");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }

}
