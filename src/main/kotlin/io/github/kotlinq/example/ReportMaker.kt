package io.github.kotlinq.example

import io.github.kotlinq.Kotlinq

@Kotlinq
class ReportMaker {

    fun <X> makeAndPrintReport(storage: Storage<X>) {
        val totalPopulation = storage.animals()
            .map { it.population }
            .aggregate { it.sum() }
        println("Total population: $totalPopulation")

        val report = storage.species()
            .join(
                storage.animals(),
                { s, a -> s.id == a.speciesId },
                { s, a -> AnimalDescription(s.name, a.name, a.population / Thousand) }
            )
            .sortedDescendingBy { it.popularityK }
            .filter { it.popularityK > 0 }
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