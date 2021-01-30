package com.mapk.kmapper

import com.mapk.common.sources.FiveArgsSrc
import com.mapk.common.targets.FiveArgs
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
    fun manual(): FiveArgs = FiveArgs(
        src.arg0,
        src.arg1,
        src.arg2,
        src.arg3,
        src.arg4,
    )
}
