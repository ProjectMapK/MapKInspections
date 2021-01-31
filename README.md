MapKInspections
====
Testing and benchmarking for ProjectMapK deliverables.

## Benchmarking
This benchmark is a simple one for `data class` with 5 fields and 20 fields.

### KMapper
- [KMapper 0.33](https://github.com/ProjectMapK/KMapper)

A comparison of `KMapper`, `BoundKMapper`, some other mapping libraries, and manual mapping.  
See also [java-object-mapper-benchmark](https://github.com/arey/java-object-mapper-benchmark) for a comparison with other mapping libraries.

#### Results
```
Benchmark                                                  Mode  Cnt         Score          Error  Units
c.m.kmapper.FiveArgsBenchmark.boundKMapper                thrpt    4   1881569.917 ±   167687.395  ops/s
c.m.kmapper.FiveArgsBenchmark.kMapper                     thrpt    4    918028.639 ±    70749.740  ops/s
c.m.kmapper.FiveArgsBenchmark.manual                      thrpt    4  51043446.918 ±  1701820.524  ops/s
c.m.kmapper.FiveArgsBenchmark.mapStruct                   thrpt    4  43095032.184 ±  1673529.682  ops/s
c.m.kmapper.FiveArgsBenchmark.mapStructConstructor        thrpt    4  41170101.739 ± 10857918.052  ops/s
c.m.kmapper.FiveArgsBenchmark.modelMapper                 thrpt    4    152843.152 ±    24307.984  ops/s
c.m.kmapper.FiveArgsBenchmark.orika                       thrpt    4   3631909.739 ±    99847.838  ops/s
c.m.kmapper.TwentyArgsBenchmark.boundKMapper              thrpt    4    529947.475 ±    20656.669  ops/s
c.m.kmapper.TwentyArgsBenchmark.kMapper                   thrpt    4    226232.207 ±    22438.183  ops/s
c.m.kmapper.TwentyArgsBenchmark.manual                    thrpt    4  21821032.043 ±   458163.893  ops/s
c.m.kmapper.TwentyArgsBenchmark.mapStruct                 thrpt    4  22312415.193 ±   477644.647  ops/s
c.m.kmapper.TwentyArgsBenchmark.mapStructConstructor      thrpt    4  18087503.877 ±  1605482.475  ops/s
c.m.kmapper.TwentyArgsBenchmark.modelMapper               thrpt    4     40300.300 ±     5979.843  ops/s
c.m.kmapper.TwentyArgsBenchmark.orika                     thrpt    4   3151262.014 ±   221691.985  ops/s
```

#### About comparison targets
The following three libraries were used for comparison.

- [MapStruct 1.4.1.Final](https://github.com/mapstruct/mapstruct)
- [Orika 1.5.4](https://github.com/orika-mapper/orika)
- [ModelMapper 2.3.9](https://github.com/modelmapper/modelmapper)

##### MapStruct
Since `1.4.0.Final` supports constructor calls, which can be used in `Kotlin`'s `data class`, it is used for comparison.

##### Orika
In the benchmark based on [java-object-mapper-benchmark](https://github.com/arey/java-object-mapper-benchmark),
it was faster than `KMapper` and `BoundKMapper`, it is used for comparison.

##### ModelMapper
This is the library that led to the creation of `KMapper`, it is used for comparison.
