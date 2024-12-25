package org.dskxvos.constructor;

import org.dskxvos.function.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        testB();
    }


    public static void testB(){
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        FluxBuilder fluxBuilder = new FluxBuilder();
        fluxBuilder.from("bucket_name")
                .range(LocalDateTime.now().minusDays(5),LocalDateTime.now().plusHours(24),zoneOffset)
                .measurement("measurement_name")
                .function(new Filter().tagEq("tag_name","7128678662206914560")
                        .and().openParen().fieldEq(FieldConstants._field,"field_name").or().fieldEq(FieldConstants._field,"another_field_name").closeParen())
                .function(new Top().n(1).columns(List.of(FieldConstants._value,FieldConstants._time)))
                .function(new Group(List.of(FieldConstants._measurement,FieldConstants._field)))
                .function(new Top().n(5).columns(List.of(FieldConstants._value)));
        String flux = fluxBuilder.build();
        System.out.println(flux);
        System.out.println(FluxTool.format(flux));

    }


}
