package com.mapk.kmapper

import com.mapk.common.sources.TwentyArgsSrc
import com.mapk.common.targets.TwentyArgs
import com.mapk.common.targets.TwentyArgs2
import ma.glasnost.orika.impl.DefaultMapperFactory
import org.mapstruct.factory.Mappers
import org.modelmapper.ModelMapper
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.util.concurrent.ThreadLocalRandom

@State(Scope.Benchmark)
class TwentyArgsBenchmark {
    private val _kMapper = KMapper(::TwentyArgs)
    private val _boundKMapper = BoundKMapper<TwentyArgsSrc, TwentyArgs>()

    private val _mapStruct: MapStructMapper = Mappers.getMapper(MapStructMapper::class.java)
    private val _mapStructConstructor: MapStructConstructorMapper =
        Mappers.getMapper(MapStructConstructorMapper::class.java)

    private val _orika = DefaultMapperFactory.Builder().build()
        .getMapperFacade(TwentyArgsSrc::class.java, TwentyArgs::class.java)

    private val _modelMapper = ModelMapper()

    private lateinit var src: TwentyArgsSrc

    @Setup(Level.Iteration)
    fun setupTarget() {
        src = TwentyArgsSrc.of(ThreadLocalRandom.current().nextInt(5))
    }

    @Benchmark
    fun kMapper(): TwentyArgs = _kMapper.map(src)

    @Benchmark
    fun boundKMapper(): TwentyArgs = _boundKMapper.map(src)

    @Benchmark
    fun mapStruct(): TwentyArgs = _mapStruct.map(src)

    @Benchmark
    fun mapStructConstructor(): TwentyArgs2 = _mapStructConstructor.map(src)

    @Benchmark
    fun orika(): TwentyArgs = _orika.map(src)

    @Benchmark
    fun modelMapper(): TwentyArgs = _modelMapper.map(src, TwentyArgs::class.java)

    @Benchmark
    fun manual(): TwentyArgs = TwentyArgs(
        src.arg00,
        src.arg01,
        src.arg02,
        src.arg03,
        src.arg04,
        src.arg05,
        src.arg06,
        src.arg07,
        src.arg08,
        src.arg09,
        src.arg10,
        src.arg11,
        src.arg12,
        src.arg13,
        src.arg14,
        src.arg15,
        src.arg16,
        src.arg17,
        src.arg18,
        src.arg19
    )
}
