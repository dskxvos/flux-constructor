package org.dskxvos.constructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FluxBuilder {

    private String bucket;
    private String measurement;
    private ZoneOffset zoneOffset;
    private LocalDateTime start;
    private LocalDateTime stop;

    private StringBuilder fluxStatement;

    private FluxBuilder (){
        fluxStatementBuild();
    }

    private FluxBuilder(String bucket, String measurement, ZoneOffset zoneOffset, LocalDateTime start,LocalDateTime stop) {
        this.bucket = bucket;
        this.measurement = measurement;
        this.zoneOffset = zoneOffset;
        this.start = start;
        this.stop = stop;
        fluxStatementBuild();
    }

    private void fluxStatementBuild(){
        fluxStatement = new StringBuilder(
                FiledConstants.from+"( \""+FiledConstants.BUCKET_PLACEHOLDER+"\")" +
                        FiledConstants.pipeOperator+FiledConstants.range+"("+FiledConstants.RANGE_PLACEHOLDER+")"+
                        FiledConstants.filter+"("+FiledConstants.fn+": (r)=> r."+FiledConstants._measurement+" == \""+FiledConstants.MEASUREMENT_PLACEHOLDER+"\")"
        );
    }

    public static FluxBuilder instance(){
        FluxBuilder fluxBuilder = new FluxBuilder();
        return fluxBuilder;
    }

    public static FluxBuilder instance(String bucket, String measurement, ZoneOffset zoneOffset, LocalDateTime start,LocalDateTime stop){
        FluxBuilder fluxBuilder = new FluxBuilder(bucket, measurement, zoneOffset, start, stop);
        return fluxBuilder;
    }

    public static FluxBuilder instance(String bucket, String measurement, ZoneOffset zoneOffset,LocalDateTime stop){
        FluxBuilder fluxBuilder = new FluxBuilder(bucket,measurement,zoneOffset,null,stop);
        return fluxBuilder;
    }


    public static FluxBuilder instance(String bucket, String measurement,LocalDateTime start,ZoneOffset zoneOffset){
        FluxBuilder fluxBuilder = new FluxBuilder(bucket,measurement,zoneOffset,start,null);
        return fluxBuilder;
    }

    public FluxBuilder from(String bucket){
        this.bucket = bucket;
        return this;
    }

    public FluxBuilder ZoneOffset(ZoneOffset zoneOffset){
        this.zoneOffset = zoneOffset;
        return this;
    }

    public FluxBuilder range(LocalDateTime start,LocalDateTime stop){
        this.start = start;
        this.stop = stop;
        return this;
    }

    public FluxBuilder measurement(String measurement){
        this.measurement = measurement;
        return this;
    }



    private String assignment(){

        assignmentCheck();

        String statement = fluxStatement.toString();

        statement = assignmentBucket(statement);

        statement = assignmentRange(statement);

        statement = assignmentMeasurement(statement);
        return statement;
    }


    private String assignmentBucket(String statement){
       return statement.replace(FiledConstants.BUCKET_PLACEHOLDER,this.bucket);
    }

    private String assignmentRange(String statement){
        StringBuilder rangeArgs = new StringBuilder();
        if (null != this.start){
            rangeArgs.append(FiledConstants.start+": "+this.start.toInstant(zoneOffset));
        }
        if (null != this.start && null != this.stop){
            rangeArgs.append(",");
        }
        if (null != this.stop){
            rangeArgs.append(FiledConstants.stop+": "+this.stop.toInstant(zoneOffset));
        }
        return statement.replace(FiledConstants.range,rangeArgs);
    }

    private String assignmentMeasurement(String statement){
        return statement.replace(FiledConstants.MEASUREMENT_PLACEHOLDER,this.measurement);
    }

    private void assignmentCheck(){
        if (null == this.bucket || "".equals(this.bucket.trim())){
            throw new RuntimeException("bucket cannot be empty");
        }

        if (null == this.start && null == this.stop){
            throw new RuntimeException("range must have an argument, start or stop");
        }

        if (null == this.measurement || "".equals(this.measurement.trim())){
            throw new RuntimeException("measurement cannot be empty");
        }

        if (null == this.zoneOffset){
            throw new RuntimeException("zoneOffset cannot be empty");
        }



    }
}
