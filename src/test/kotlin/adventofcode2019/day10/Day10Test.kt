package adventofcode2019.day10

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ArgumentConverter
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource

class Day10Test {

    @Test
    fun `parses asteroid locations`() {
        val locations = AsteroidMap(listOf("##", "..", ".#")).asteroidLocations
        assertThat(locations, List<Pair<Int,Int>>::containsAll, listOf(0 to 0, 1 to 0, 1 to 2))
    }

    @Test
    fun `confirms asteroid can see asteroid in line of sight diagonally`() {
        val map = AsteroidMap(listOf(".#", "#."))
        assertThat(map.isInLineOfSight(1 to 0, 0 to 1), equalTo(true))
    }

    @Test
    fun `cannot see asteroid when blocked in line of sight non-standard compass direction`() {
        val map = AsteroidMap(listOf(".#...", ".....", "..#..", ".....", "...#.")) // Blocked by 2,2
        assertThat(map.isInLineOfSight(1 to 0, 3 to 4), equalTo(false))
    }

    @Test
    fun `confirms asteroid can see asteroid vertically from it`() {
        val map = AsteroidMap(listOf(".#", ".#"))
        assertThat(map.isInLineOfSight(1 to 0, 1 to 1), equalTo(true))
    }

    @Test
    fun `confirms asteroid can see asteroid in non-standard compass direction`() {
        val map = AsteroidMap(listOf("#..", "..#"))
        assertThat(map.isInLineOfSight(1 to 0, 0 to 2), equalTo(true))
    }

    @Test
    fun `not in line of sight of itself`() {
        val map = AsteroidMap(listOf("#", "."))
        assertThat(map.isInLineOfSight(0 to 0, 0 to 0), equalTo(false))
    }

    @Test
    fun `not in line of sight when asteroid between it`() {
        val map = AsteroidMap(listOf(".#", ".#", ".#")) // (1,0) cannot see (1,2) because (1,1) is in the way
        assertThat(map.isInLineOfSight(1 to 0, 1 to 2), equalTo(false))
    }

    @Test
    fun `when only one asteroid result is that`() {
        assertThat(AsteroidMap(listOf(".#", "..")).bestMonitoringStationLocation()?.first, equalTo(1 to 0))
    }

    @Test
    fun `finds best monitoring station location by number of asteroids it can see`() {
        assertThat(map1.bestMonitoringStationLocation(), equalTo((3 to 4) to 8))
        assertThat(map2.bestMonitoringStationLocation(), equalTo((5 to 8) to 33))
        assertThat(map3.bestMonitoringStationLocation(), equalTo((1 to 2) to 35))
        assertThat(map4.bestMonitoringStationLocation(), equalTo((6 to 3) to 41))
        assertThat(map5.bestMonitoringStationLocation(), equalTo((11 to 13) to 210))
    }

    private fun asteroidMap(map: String) = AsteroidMap(map.trimIndent().lineSequence().toList())

    // Example maps
    private val map1 = asteroidMap("""
.#..#
.....
#####
....#
...##
        """)

    private val map2 = asteroidMap("""
......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....#### 
    """)

    private val map3 = asteroidMap("""
#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.
    """)

    private val map4 = asteroidMap("""
.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..
    """)

    private val map5 = asteroidMap("""
.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##
    """)
}

