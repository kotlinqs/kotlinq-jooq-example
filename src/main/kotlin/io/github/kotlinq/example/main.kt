package io.github.kotlinq.example

import io.github.kotlinq.Kotlinq
import io.github.kotlinq.jooq.selectQueryableFrom
import org.jooq.generated.public_.Tables
import org.jooq.generated.public_.tables.records.AnimalsRecord
import org.jooq.impl.DSL

const val Thousand = 1000

@Kotlinq
fun main() {
    DSL.using("jdbc:h2:./example").use { dsl ->
        val totalPopulation = dsl.selectQueryableFrom(Tables.ANIMALS)
            .map { it.population }
            .aggregate { it.sum() }
        println("Total population: $totalPopulation")

        val report = dsl.selectQueryableFrom(Tables.SPECIES)
            .join<AnimalsRecord, AnimalDescription>(
                dsl.selectQueryableFrom(Tables.ANIMALS)
                    .filter { it.population > Thousand },
                { s, a -> s.id == a.speciesId }
            ) { s, a -> AnimalDescription(s.name, a.name, a.population / Thousand) }
            .sortedDescendingBy { it.popularityK }
            .map { "${it.speciesName.uppercase()}: ${it.animalName}  [${it.popularityK}K]" }
            .toList()
            .joinToString("\n")
        println(report)

    }
}

data class AnimalDescription(
    val speciesName: String,
    val animalName: String,
    val popularityK: Int
)