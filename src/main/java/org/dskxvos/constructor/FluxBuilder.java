package org.dskxvos.constructor;

import org.dskxvos.function.Function;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FluxBuilder {

    public final static String BUCKET_PLACEHOLDER = "#BUCKET_PLACEHOLDER";

    public final static String RANGE_PLACEHOLDER = "#RANGE_PLACEHOLDER";

    public final static String MEASUREMENT_PLACEHOLDER = "#MEASUREMENT_PLACEHOLDER";

    private String bucket;
    private String measurement;
    private Instant start;
    private Instant stop;

    private StringBuilder fluxStatement;

    public FluxBuilder (){
        fluxStatementBuild();
    }

    private FluxBuilder(String bucket,String measurement,ZoneOffset zoneOffset,LocalDateTime start,LocalDateTime stop) {
        if (null == zoneOffset){
            throw new RuntimeException("zoneOffset cannot be empty");
        }
        this.bucket = bucket;
        this.measurement = measurement;
        if (null != start){
            this.start = start.toInstant(zoneOffset);
        }
        if (null != stop){
            this.stop = stop.toInstant(zoneOffset);
        }
        fluxStatementBuild();
    }

    private FluxBuilder(String bucket, String measurement,Instant start,Instant stop) {
        this.bucket = bucket;
        this.measurement = measurement;
        this.start = start;
        this.stop = stop;
        fluxStatementBuild();
    }

    private void fluxStatementBuild(){
        fluxStatement = new StringBuilder(
                FieldConstants.from+"("+ FieldConstants.bucket+": \""+BUCKET_PLACEHOLDER+"\")" +
                        FieldConstants.pipeOperator+ FieldConstants.range+"("+RANGE_PLACEHOLDER+")"+
                        FieldConstants.pipeOperator+ FieldConstants.filter+"("+ FieldConstants.fn+": (r)=> r."+ FieldConstants._measurement+" == \""+MEASUREMENT_PLACEHOLDER+"\")"
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


    public static FluxBuilder instance(String bucket, String measurement,Instant start,Instant stop){
        FluxBuilder fluxBuilder = new FluxBuilder(bucket, measurement,start, stop);
        return fluxBuilder;
    }

    public FluxBuilder from(String bucket){
        this.bucket = bucket;
        return this;
    }


    public FluxBuilder range(LocalDateTime start,LocalDateTime stop,ZoneOffset zoneOffset){
        if (null != start){
            this.start = start.toInstant(zoneOffset);
        }
        if (null != stop){
            this.stop = stop.toInstant(zoneOffset);
        }
        return this;
    }


    public FluxBuilder range(Instant start, Instant stop){
        this.start = start;
        this.stop = stop;
        return this;
    }

    public FluxBuilder measurement(String measurement){
        this.measurement = measurement;
        return this;
    }

    public FluxBuilder function(Function function){
        fluxStatement.append(function.toStatement());
        return this;
    }

    public String build(){
        return assignment();
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
        return statement.replace(BUCKET_PLACEHOLDER,this.bucket);
    }

    private String assignmentRange(String statement){
        StringBuilder rangeArgs = new StringBuilder();
        if (null != this.start){
            rangeArgs.append(FieldConstants.start+": "+this.start);
        }
        if (null != this.start && null != this.stop){
            rangeArgs.append(",");
        }
        if (null != this.stop){
            rangeArgs.append(FieldConstants.stop+": "+this.stop);
        }
        return statement.replace(RANGE_PLACEHOLDER,rangeArgs);
    }

    private String assignmentMeasurement(String statement){
        return statement.replace(MEASUREMENT_PLACEHOLDER,this.measurement);
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

    }

}
