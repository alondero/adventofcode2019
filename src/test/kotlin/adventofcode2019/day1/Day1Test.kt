package adventofcode2019.day1

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day1Test {

    @ParameterizedTest(name = "mass: {0}, fuel required: {1}")
    @CsvSource(
        "0,0",
        "12,2",
        "14,2",
        "33,9",
        "1969,654",
        "100756,33583"
    )
    fun `Calculates fuel`(mass: Double, fuelRequired: Double) {
        assertThat(calculate(mass), equalTo(fuelRequired))
    }

    @ParameterizedTest(name = "mass: {0}, fuel required: {1}")
    @CsvSource(
        "0,0",
        "12,2",
        "14,2",
        "33,10",
        "1969,966",
        "100756,50346"
    )
    fun `Calculates fuel including for extra fuel`(mass: Double, fuelRequired: Double) {
        assertThat(calculateIncludingFuelMass(mass), equalTo(fuelRequired))
    }
}