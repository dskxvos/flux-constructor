package org.dskxvos.constructor;

import org.dskxvos.function.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        testA();
        testB();
    }


    public static void testB(){
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        FluxBuilder fluxBuilder = new FluxBuilder();
        fluxBuilder.from("Vehicle")
                .range(LocalDateTime.now().minusDays(5),LocalDateTime.now().plusHours(24),zoneOffset)
                .measurement("vehicle_trajectory")
                .function(new Filter().fieldEq(FieldConstants._field,"todayAccDuration"))
                .function(new Top().n(1).columns(Arrays.asList(FieldConstants._value,FieldConstants._time)))
                .function(new Group(Arrays.asList(FieldConstants._measurement,"_field")))
                .function(new Top().n(5).columns(List.of("_value")));
        System.out.println(fluxBuilder.build());
    }

    public static void testA(){
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        FluxBuilder fluxBuilder = new FluxBuilder();
        fluxBuilder.from("Vehicle")
                .range(LocalDateTime.now().minusDays(5),LocalDateTime.now().plusHours(24),zoneOffset)
                .measurement("vehicle_trajectory")
                .function(new Filter().tagEq("carId","7128678662206914560")
                        .and().openParen().fieldEq("_field","address").or().fieldEq("_field","platformMileage").closeParen())
                .function(new Sort(true,"_time"))
                .function(new First("_time"))
                .function(new Pivot().rowKey(Arrays.asList("_time","carId")).columnKey(Arrays.asList("_field")).valueColumn("_value"));
        System.out.println(fluxBuilder.build());
    }
}
