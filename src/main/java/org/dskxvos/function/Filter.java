package org.dskxvos.function;

import org.dskxvos.constructor.FiledConstants;

public class Filter implements Function{


    private StringBuilder fluxStatement;

    private static final String predicate = "r";

    public Filter() {
        fluxStatement = new StringBuilder(FiledConstants.pipeOperator+FiledConstants.filter+"("+FiledConstants.fn+":("+predicate+")=> ");
    }

    @Override
    public String toStatement() {
        return fluxStatement.toString()+")";
    }


    public Filter tagEq(String tag,String value){
        if (null == tag||"".equals(tag.trim())){
            throw new RuntimeException("tag cannot be empty");
        }
        if (null == value||"".equals(value.trim())){
            throw new RuntimeException("value cannot be empty");
        }
        fluxStatement.append(predicate+"."+tag+Comparator.Equal.expression+"\""+value+"\"");
        return this;
    }

    public Filter and(){
        fluxStatement.append(Connector.And.expression);
        return this;
    }

    public Filter or(){
        fluxStatement.append(Connector.Or.expression);
        return this;
    }

    public Filter fieldEq(String field,Object value){
        if (null == field||"".equals(field.trim())){
            throw new RuntimeException("field cannot be empty");
        }
        if (null == value){
            throw new RuntimeException("value cannot be null");
        }
        if (value instanceof String){
            String valueStr = value.toString();
            if ("".equals(valueStr.trim())){
                throw new RuntimeException("value cannot be empty");
            }
            fluxStatement.append(predicate+"."+field+Comparator.Equal.expression+"\""+value+"\"");
        }else {
            fluxStatement.append(predicate+"."+field+Comparator.Equal.expression+value);
        }
        return this;
    }

    public Filter openParen(){
        fluxStatement.append(" ( ");
        return this;
    }

    public Filter closeParen(){
        fluxStatement.append(" ) ");
        return this;
    }


    public static class Condition{

        private String key;

        private Comparator comparator;

        private String value;

    }


    private static enum Comparator{
        Equal(" == "),
        GreaterThan(" > "),
        LessThan(" < "),
        GreatThanOrEqualTo(" >= "),
        LessThanOrEqualTo(" <= ");

        private String expression;

        Comparator(String expression) {
            this.expression = expression;
        }
    }

    private static enum Connector{
        And(" and "),
        Or(" or ");
        private String expression;

        Connector(String expression) {
            this.expression = expression;
        }
    }
}
