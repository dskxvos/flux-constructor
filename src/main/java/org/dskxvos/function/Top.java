package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Collection;

public class Top implements Function{

    private final static String N_PLACEHOLDER = "#N_PLACEHOLDER";
    private final static String COLUMNS_PLACEHOLDER = "#COLUMNS_PLACEHOLDER";

    private Long number;

    private Collection<String> columns;

    private StringBuilder fluxStatement;

    public Top() {
    }

    public Top(Long n, Collection<String> columns) {
        this.number = n;
        this.columns = columns;
    }


    private void statementBuild(){
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.top+"("+FiledConstants.n+":"+N_PLACEHOLDER+","
                +FiledConstants.columns+":["+COLUMNS_PLACEHOLDER+"])");
    }


    public Top n(long n){
        this.number = n;
        return this;
    }

    public Top columns(Collection<String> columns){
        this.columns = columns;
        return this;
    }

    @Override
    public String toStatement() {
        assignmentCheck();
        String statement = fluxStatement.toString();
        statement = statement.replace(N_PLACEHOLDER,this.number.toString());
        statement = statement.replace(COLUMNS_PLACEHOLDER, StringUtils.quotationMark(this.columns));
        return statement;
    }

    private void assignmentCheck(){
        if (null == this.number){
            throw new RuntimeException("The n argument of function pivot cannot be null");
        }
        if (null == this.columns || this.columns.size()==0){
            throw new RuntimeException("The columns argument of function pivot cannot be null");
        }
    }
}
