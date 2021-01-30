package com.mapk.krowmapper

import com.mapk.common.targets.FiveArgs
import org.h2.jdbcx.JdbcDataSource
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import java.sql.ResultSet
import java.util.concurrent.ThreadLocalRandom
import javax.sql.DataSource

@State(Scope.Benchmark)
class FiveArgsBenchmark {
    private lateinit var dataSource: DataSource

    private lateinit var resultSet: ResultSet

    private val _kRowMapper = KRowMapper(::FiveArgs)
    private val _beanPropertyRowMapper = BeanPropertyRowMapper(FiveArgs::class.java)

    @Setup(Level.Trial)
    fun setup() {
        dataSource = JdbcDataSource().apply {
            setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS APP\\;SET SCHEMA APP;")
        }

        val jdbcTemplate = JdbcTemplate(dataSource)

        jdbcTemplate.execute(DROP_TABLE_QUERY)
        jdbcTemplate.execute(CREATE_TABLE_QUERY)

        (0..4).map { BeanPropertySqlParameterSource(FiveArgs(it, it, it, it ,it)) }
            .also { SimpleJdbcInsert(jdbcTemplate).withTableName("five_arg_table").executeBatch(*it.toTypedArray()) }
    }

    @Setup(Level.Iteration)
    fun setupTarget() {
        resultSet = dataSource.connection.createStatement()
            .executeQuery("SELECT * FROM `five_arg_table` WHERE `arg0` = ${ThreadLocalRandom.current().nextInt(5)}")
            .apply { first() } // 正常に読むにはfirstする必要が有る
    }

    @Benchmark
    fun beanPropertyRowMapper(): FiveArgs? = _beanPropertyRowMapper.mapRow(resultSet, 0)

    @Benchmark
    fun kRowMapper(): FiveArgs? = _kRowMapper.mapRow(resultSet, 0)

    @Benchmark
    fun manual(): FiveArgs? = FiveArgs(
        resultSet.getInt("arg0"),
        resultSet.getInt("arg1"),
        resultSet.getInt("arg2"),
        resultSet.getInt("arg3"),
        resultSet.getInt("arg4")
    )

    @TearDown
    fun tearDown() {
        dataSource.connection.close()
    }

    companion object {
        val DROP_TABLE_QUERY = """
            DROP TABLE IF EXISTS five_arg_table;
        """.trimIndent()

        val CREATE_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS `five_arg_table` (
              `arg0` INT UNSIGNED NOT NULL,
              `arg1` INT UNSIGNED NOT NULL,
              `arg2` INT UNSIGNED NOT NULL,
              `arg3` INT UNSIGNED NOT NULL,
              `arg4` INT UNSIGNED NOT NULL,
              PRIMARY KEY (`arg0`)
            );
        """.trimIndent()
    }
}
