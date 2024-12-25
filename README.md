# flux-constructor
A JAVA LIBRARY FOR BUILD FLUX STATEMENTS
Example:

```java
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        FluxBuilder fluxBuilder = new FluxBuilder();
        fluxBuilder.from("bucket_name")
                .range(LocalDateTime.now().minusDays(5),LocalDateTime.now().plusHours(24),zoneOffset)
                .measurement("measurement_name")
                .function(new Filter().tagEq("tag_name","7128678662206914560")
                        .and().openParen().fieldEq(FieldConstants._field,"field_name").or().fieldEq(FieldConstants._field,"another_field_name").closeParen())
                .function(new Top().n(1).columns(Arrays.asList(FieldConstants._value,FieldConstants._time)))
                .function(new Group(Arrays.asList(FieldConstants._measurement,FieldConstants._field)))
                .function(new Top().n(5).columns(List.of(FieldConstants._value)));
        String flux = fluxBuilder.build();
        System.out.println(flux);
        System.out.println(FluxTool.format(flux));
```

```

from(bucket: "bucket_name")|> range(start: 2024-12-20T01:56:37.698524900Z,stop: 2024-12-26T01:56:37.698524900Z)|> filter(fn: (r)=> r._measurement == "measurement_name")|> filter(fn:(r)=> r.tag_name == "7128678662206914560" and  ( r._field == "field_name" or r._field == "another_field_name" ) )|> top(n:1,columns:["_value","_time"])|> group(columns:["_measurement","_field"])|> top(n:5,columns:["_value"])


from(bucket: "bucket_name")
|> range(start: 2024-12-20T01:56:37.698524900Z,stop: 2024-12-26T01:56:37.698524900Z)
|> filter(fn: (r)=> r._measurement == "measurement_name")
|> filter(fn:(r)=> r.tag_name == "7128678662206914560" and  ( r._field == "field_name" or r._field == "another_field_name" ) )
|> top(n:1,columns:["_value","_time"])
|> group(columns:["_measurement","_field"])
|> top(n:5,columns:["_value"])
```

