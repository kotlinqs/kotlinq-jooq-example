package io.github.kotlinq.example

import io.github.kotlinq.collections.DataQueryable
import org.jooq.generated.public_.tables.records.AnimalsRecord
import org.jooq.generated.public_.tables.records.SpeciesRecord

interface Storage<X> {
    fun animals(): DataQueryable<AnimalsRecord, X>

    fun species(): DataQueryable<SpeciesRecord, X>
}