package com.mapk.krowmapper

import com.mapk.common.targets.TwentyArgs
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
class TwentyArgsBenchmark {
    private lateinit var dataSource: DataSource

    private lateinit var resultSet: ResultSet

    private val _kRowMapper = KRowMapper(::TwentyArgs)
    private val _beanPropertyRowMapper = BeanPropertyRowMapper(TwentyArgs::class.java)

    @Setup(Level.Trial)
    fun setup() {
        dataSource = JdbcDataSource().apply {
            setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS APP\\;SET SCHEMA APP;")
        }

        val jdbcTemplate = JdbcTemplate(dataSource)

        jdbcTemplate.execute(DROP_TABLE_QUERY)
        jdbcTemplate.execute(CREATE_TABLE_QUERY)

        (0..19).map { BeanPropertySqlParameterSource(TwentyArgs.of(it)) }
            .also { SimpleJdbcInsert(jdbcTemplate).withTableName("twenty_arg_table").executeBatch(*it.toTypedArray()) }
    }

    @Setup(Level.Iteration)
    fun setupTarget() {
        resultSet = dataSource.connection.createStatement()
            .executeQuery("SELECT * FROM `twenty_arg_table` WHERE `arg00` = ${ThreadLocalRandom.current().nextInt(20)}")
            .apply { first() } // 正常に読むにはfirstする必要が有る
    }

    @Benchmark
    fun beanPropertyRowMapper(): TwentyArgs? = _beanPropertyRowMapper.mapRow(resultSet, 0)

    @Benchmark
    fun kRowMapper(): TwentyArgs? = _kRowMapper.mapRow(resultSet, 0)

    @Benchmark
    fun manual(): TwentyArgs? = TwentyArgs(
        resultSet.getInt("arg00"),
        resultSet.getInt("arg01"),
        resultSet.getInt("arg02"),
        resultSet.getInt("arg03"),
        resultSet.getInt("arg04"),
        resultSet.getInt("arg05"),
        resultSet.getInt("arg06"),
        resultSet.getInt("arg07"),
        resultSet.getInt("arg08"),
        resultSet.getInt("arg09"),
        resultSet.getInt("arg10"),
        resultSet.getInt("arg11"),
        resultSet.getInt("arg12"),
        resultSet.getInt("arg13"),
        resultSet.getInt("arg14"),
        resultSet.getInt("arg15"),
        resultSet.getInt("arg16"),
        resultSet.getInt("arg17"),
        resultSet.getInt("arg18"),
        resultSet.getInt("arg19"),
    )

    @TearDown
    fun tearDown() {
        dataSource.connection.close()
    }

    companion object {
        val DROP_TABLE_QUERY = """
            DROP TABLE IF EXISTS twenty_arg_table;
        """.trimIndent()

        val CREATE_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS `twenty_arg_table` (
              `arg00` INT UNSIGNED NOT NULL,
              `arg01` INT UNSIGNED NOT NULL,
              `arg02` INT UNSIGNED NOT NULL,
              `arg03` INT UNSIGNED NOT NULL,
              `arg04` INT UNSIGNED NOT NULL,
              `arg05` INT UNSIGNED NOT NULL,
              `arg06` INT UNSIGNED NOT NULL,
              `arg07` INT UNSIGNED NOT NULL,
              `arg08` INT UNSIGNED NOT NULL,
              `arg09` INT UNSIGNED NOT NULL,
              `arg10` INT UNSIGNED NOT NULL,
              `arg11` INT UNSIGNED NOT NULL,
              `arg12` INT UNSIGNED NOT NULL,
              `arg13` INT UNSIGNED NOT NULL,
              `arg14` INT UNSIGNED NOT NULL,
              `arg15` INT UNSIGNED NOT NULL,
              `arg16` INT UNSIGNED NOT NULL,
              `arg17` INT UNSIGNED NOT NULL,
              `arg18` INT UNSIGNED NOT NULL,
              `arg19` INT UNSIGNED NOT NULL,
              PRIMARY KEY (`arg00`)
            );
        """.trimIndent()
    }
}
