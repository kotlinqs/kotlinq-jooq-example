package io.github.kotlinq.example

import io.github.kotlinq.collections.DataQueryable
import io.github.kotlinq.collections.QS
import io.github.kotlinq.collections.asQueryable
import org.jooq.generated.public_.tables.records.AnimalsRecord
import org.jooq.generated.public_.tables.records.SpeciesRecord

class MemoryStorage(
    private val animals: List<AnimalsRecord>,
    private val species: List<SpeciesRecord>,
): Storage<QS> {
    override fun animals(): DataQueryable<AnimalsRecord, QS> {
        return animals.asSequence().asQueryable()
    }

    override fun species(): DataQueryable<SpeciesRecord, QS> {
        return species.asSequence().asQueryable()
    }
}