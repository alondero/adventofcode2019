package adventofcode2019.day6

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class Day6Test {
    @Test
    fun `object orbiting COM has 1 Orbit`() {
        assertThat(numberOfOrbits("SUN)EARTH"), equalTo(1))
    }

    @Test
    fun `object transitively orbits another orbiting object counts`() {
        assertThat(numberOfOrbits("SUN)EARTH\nEARTH)MOON\nMOON)WALLACE"), equalTo(6))
    }

    @Test
    fun `handles multiple orbits of one object`() {
        assertThat(numberOfOrbits("SUN)EARTH\nSUN)VENUS\nSUN)MERCURY"), equalTo(3))
    }

    @Test
    fun `handles different branch lengths`() {
        assertThat(numberOfOrbits("SUN)EARTH\nSUN)VENUS\nSUN)MERCURY\nEARTH)MOON"), equalTo(5))
    }

    @Test
    fun `passes example`() {
        val exampleMap = "COM)B\nB)C\nC)D\nD)E\nE)F\nB)G\nG)H\nD)I\nE)J\nJ)K\nK)L"

        assertThat(numberOfOrbits(exampleMap), equalTo(42))
    }

    @Test
    fun `handles not having COM immediately`() {
        assertThat(numberOfOrbits("EARTH)MOON\nSUN)EARTH"), equalTo(3))
    }

    private fun numberOfOrbits(orbitMap: String): Int {
        val orbitalSystem = orbitalSystemFrom(orbitMap)
        return orbitalSystem.numberOfOrbits()
    }
}