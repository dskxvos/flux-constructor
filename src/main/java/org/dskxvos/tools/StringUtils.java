package org.dskxvos.tools;

import java.util.Collection;

public class StringUtils {

    public static final String quotationMark(Collection<String> args){
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append("\""+arg+"\",");
        }
        return builder.substring(0,builder.length()-1);
    }
}
