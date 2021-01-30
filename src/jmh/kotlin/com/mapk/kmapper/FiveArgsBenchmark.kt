package com.mapk.kmapper

import com.mapk.common.sources.FiveArgsSrc
import com.mapk.common.targets.FiveArgs
import com.mapk.common.targets.FiveArgs2
import ma.glasnost.orika.impl.DefaultMapperFactory
import org.mapstruct.factory.Mappers
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.util.concurrent.ThreadLocalRandom

@State(Scope.Benchmark)
class FiveArgsBenchmark {
    private val _kMapper = KMapper(::FiveArgs)
    private val _boundKMapper = BoundKMapper<FiveArgsSrc, FiveArgs>()

    private val _mapStruct: MapStructMapper = Mappers.getMapper(MapStructMapper::class.java)
    private val _mapStructConstructor: MapStructConstructorMapper =
        Mappers.getMapper(MapStructConstructorMapper::class.java)

    private val _orika = DefaultMapperFactory.Builder().build()
        .getMapperFacade(FiveArgsSrc::class.java, FiveArgs::class.java)

    private lateinit var src: FiveArgsSrc

    @Setup(Level.Iteration)
    fun setupTarget() {
        val i = ThreadLocalRandom.current().nextInt(5)
        src = FiveArgsSrc(i, i, i, i, i)
    }

    @Benchmark
    fun kMapper(): FiveArgs = _kMapper.map(src)

    @Benchmark
    fun boundKMapper(): FiveArgs = _boundKMapper.map(src)

    @Benchmark
    fun mapStruct(): FiveArgs = _mapStruct.map(src)

    @Benchmark
    fun mapStructConstructor(): FiveArgs2 = _mapStructConstructor.map(src)

    @Benchmark
    fun orika(): FiveArgs = _orika.map(src)

    @Benchmark
    fun manual(): FiveArgs = FiveArgs(
        src.arg0,
        src.arg1,
        src.arg2,
        src.arg3,
        src.arg4,
    )
}
