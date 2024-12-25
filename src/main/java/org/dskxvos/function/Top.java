package org.dskxvos.function;

import org.dskxvos.constructor.FieldConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Collection;

public class Top implements Function{

    private final static String N_PLACEHOLDER = "#N_PLACEHOLDER";
    private final static String COLUMNS_PLACEHOLDER = "#COLUMNS_PLACEHOLDER";

    private Long number;

    private Collection<String> columns;

    private StringBuilder fluxStatement;

    public Top() {
        statementBuild();
    }

    public Top(Long n, Collection<String> columns) {
        this.number = n;
        this.columns = columns;
        statementBuild();
    }


    private void statementBuild(){
        fluxStatement = new StringBuilder(FieldConstants.pipeOperator+ FieldConstants.top+"("+ FieldConstants.n+":"+N_PLACEHOLDER+","
                + FieldConstants.columns+":["+COLUMNS_PLACEHOLDER+"])");
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
        String statement = fluxStatement.toString();
        statement = statement.replace(N_PLACEHOLDER,this.number.toString());
        statement = statement.replace(COLUMNS_PLACEHOLDER, StringUtils.quotationMark(this.columns));
        return statement;
    }


    @Override
    public void argsCheck() {
        if (null == this.number){
            throw new RuntimeException("The n argument of function top cannot be null");
        }
        if (null == this.columns || this.columns.size()==0){
            throw new RuntimeException("The columns argument of function top cannot be null");
        }
    }

}
