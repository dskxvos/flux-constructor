package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Collection;

public class Group implements Function{


    private StringBuilder fluxStatement;


    public Group(Collection<String> columns) {
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.group+"("+FiledConstants.columns+":["+ StringUtils.quotationMark(columns) +"])");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString();
    }
}
