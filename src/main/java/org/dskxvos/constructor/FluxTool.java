package org.dskxvos.constructor;

public class FluxTool {

    public static final String format (String flux){
        return flux.replaceAll("\\"+FieldConstants.pipeOperator,"\n"+FieldConstants.pipeOperator);
    }
}
