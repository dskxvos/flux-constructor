package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;
import org.dskxvos.tools.StringUtils;

import java.util.Collection;

public class Pivot implements Function{


    private final static String ROWKEY_PLACEHOLDER = "#ROWKEY_PLACEHOLDER";
    private final static String COLUMNKEY_PLACEHOLDER = "#COLUMNKEY_PLACEHOLDER";
    private final static String VALUECOLUMN_PLACEHOLDER = "#VALUECOLUMN_PLACEHOLDER";

    private Collection<String> rowKey;

    private Collection<String> columnKey;

    private String valueColumn;

    private StringBuilder fluxStatement;

    public Pivot(){
        statementBuild();
    }

    public Pivot(Collection<String> rowKey, Collection<String> columnKey, String valueColumn) {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        this.valueColumn = valueColumn;
        statementBuild();
    }

    private void statementBuild(){
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.pivot
                +"("+FiledConstants.rowKey+":["+ROWKEY_PLACEHOLDER+"]," +
                FiledConstants.columnKey+":["+COLUMNKEY_PLACEHOLDER+"]," +
                FiledConstants.valueColumn+":"+VALUECOLUMN_PLACEHOLDER+")");
    }


    public Pivot rowKey(Collection<String> rowKey){
        this.rowKey = rowKey;
        return this;
    }

    public Pivot columnKey(Collection<String> columnKey){
        this.columnKey = columnKey;
        return this;
    }

    public Pivot valueColumn(String valueColumn){
        this.valueColumn = valueColumn;
        return this;
    }

    @Override
    public String toStatement() {
        assignmentCheck();

        // rowKey
        String statement = fluxStatement.toString();
        statement = statement.replace(ROWKEY_PLACEHOLDER,StringUtils.quotationMark(this.rowKey));

        // columnKey
        statement = statement.replace(COLUMNKEY_PLACEHOLDER, StringUtils.quotationMark(this.columnKey));

        // valueColumn
        statement = statement.replace(VALUECOLUMN_PLACEHOLDER,"\""+this.valueColumn+"\"");

        return statement;
    }

    private void assignmentCheck(){
        if (null == this.rowKey || this.rowKey.size()==0){
            throw new RuntimeException("The rowKey argument of function pivot cannot be null");
        }

        if (null == this.columnKey || this.columnKey.size()==0){
            throw new RuntimeException("The columnKey argument of function pivot cannot be null");
        }

        if (null == this.valueColumn||"".equals(valueColumn.trim())){
            throw new RuntimeException("The valueColumn argument of function pivot cannot be null");
        }
    }
}
