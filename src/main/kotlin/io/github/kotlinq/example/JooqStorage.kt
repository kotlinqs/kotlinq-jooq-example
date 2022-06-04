package io.github.kotlinq.example

import io.github.kotlinq.collections.DataQueryable
import io.github.kotlinq.jooq.Jooq
import io.github.kotlinq.jooq.selectQueryableFrom
import org.jooq.DSLContext
import org.jooq.generated.public_.Tables
import org.jooq.generated.public_.tables.records.AnimalsRecord
import org.jooq.generated.public_.tables.records.SpeciesRecord

class JooqStorage(private val dslContext: DSLContext) : Storage<Jooq> {
    override fun animals(): DataQueryable<AnimalsRecord, Jooq> {
        return dslContext.selectQueryableFrom(Tables.ANIMALS)
    }

    override fun species(): DataQueryable<SpeciesRecord, Jooq> {
        return dslContext.selectQueryableFrom(Tables.SPECIES)
    }
}