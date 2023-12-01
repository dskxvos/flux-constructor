package org.dskxvos.function;

public class Filter implements Function{


    @Override
    public String toStatement() {
        return null;
    }


    public static class Condition{

        private String key;

        private Comparator comparator;

        private String value;

    }


    public static enum Comparator{
        Equal,
        GreaterThan,
        LessThan,
        GreatThanOrEqualTo,
        LessThanOrEqualTo;
    }
}
