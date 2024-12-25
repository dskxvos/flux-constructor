package org.dskxvos.function;

public interface Function {

    String toStatement();

    default void argsCheck(){};

}
