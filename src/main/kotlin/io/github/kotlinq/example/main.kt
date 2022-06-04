package io.github.kotlinq.example

import org.jooq.ExecuteContext
import org.jooq.conf.ParamType
import org.jooq.generated.public_.Tables
import org.jooq.impl.DSL
import org.jooq.impl.DefaultExecuteListener

const val Thousand = 1000

fun main() {
    var sqlQuery = ""
    DSL.using("jdbc:h2:./example").use { dsl ->
        dsl.configuration().set(object : DefaultExecuteListener() {
            override fun executeStart(ctx: ExecuteContext) {
                sqlQuery = ctx.query()?.getSQL(ParamType.INLINED)!!
            }

        })
        println("DB Storage")
        println("================")
        ReportMaker().makeAndPrintReport(JooqStorage(dsl))
    }

    println()
    println("Memory Storage")
    println("================")
    val memoryStorage = DSL.using("jdbc:h2:./example").use { dsl ->
        MemoryStorage(
            dsl.fetch(Tables.ANIMALS).toList(),
            dsl.fetch(Tables.SPECIES).toList(),
        )
    }
    ReportMaker().makeAndPrintReport(memoryStorage)
    println()

    println()
    println("SQL query generated")
    println("================")
    println(sqlQuery
        .replace(Regex("(where|order|from)"), "\n  $1"))

}
