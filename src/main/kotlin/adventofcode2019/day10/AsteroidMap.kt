package adventofcode2019.day10

import java.io.File
import java.lang.Integer.min
import kotlin.math.abs

const val ASTEROID_CHARACTER = "#"

typealias Coordinate = Pair<Int, Int>

class AsteroidMap(rows: List<String>) {
    val asteroidLocations: List<Coordinate> = rows.withIndex().mapNotNull { (yCoord, row) ->
        row.chunked(1).withIndex().mapNotNull { (xCoord, entity) ->
            when (entity) {
                ASTEROID_CHARACTER -> (xCoord to yCoord)
                else -> null
            }
        }
    }.flatten()

    fun bestMonitoringStationLocation(): Pair<Coordinate, Int>? = asteroidLocations.map { asteroidCoord ->
            asteroidCoord to asteroidLocations.filter { isInLineOfSight(asteroidCoord, it) }.size
        }.maxBy {it.second}

    fun isInLineOfSight(asteroidA: Pair<Int, Int>, asteroidB: Pair<Int, Int>): Boolean {
        if (asteroidA == asteroidB) {
            return false
        }

        var nextPointToCheck = nextPointToCheck(asteroidA, asteroidB)

        while (nextPointToCheck != null && nextPointToCheck != asteroidB) {
            if (asteroidLocations.contains(nextPointToCheck)) {
                return false
            }

            nextPointToCheck = nextPointToCheck(nextPointToCheck, asteroidB)
        }

        return true
    }

    private fun nextPointToCheck(coordA: Pair<Int, Int>, coordB: Pair<Int, Int>): Pair<Int, Int>? = ((coordA.first - coordB.first) to (coordA.second - coordB.second)).let {(diffX, diffY) ->
        when {
            diffX == 0 && diffY == 0 -> null
            diffX == 0 -> when {
                diffY > 0 -> coordA.first to coordA.second - 1
                diffY < 0 -> coordA.first to coordA.second + 1
                else -> null
            }
            diffY == 0 -> when {
                diffX > 0 -> coordA.first - 1 to coordA.second
                diffX < 0 -> coordA.first + 1 to coordA.second
                else -> null
            }
            diffX == diffY -> when {
                diffX > 0 -> coordA.first - 1 to coordA.second - 1
                diffX < 0 -> coordA.first + 1 to coordA.second + 1
                else -> null
            }
            else -> {
                lowestCommonFactor(abs(diffX), abs(diffY))?.let { factor ->
                    coordA.first - (diffX / factor) to coordA.second - (diffY / factor)
                }
            }
        }
    }

    private fun lowestCommonFactor(a: Int, b: Int) = (2..min(a,b)).firstOrNull { a % it == 0 && b % it == 0 }

}

/*
--- Day 10: Monitoring Station ---
You fly into the asteroid belt and reach the Ceres monitoring station. The Elves here have an emergency: they're having trouble tracking all of the asteroids and can't be sure they're safe.

The Elves would like to build a new monitoring station in a nearby area of space; they hand you a map of all of the asteroids in that region (your puzzle input).

The map indicates whether each position is empty (.) or contains an asteroid (#). The asteroids are much smaller than they appear on the map, and every asteroid is exactly in the center of its marked position. The asteroids can be described with X,Y coordinates where X is the distance from the left edge and Y is the distance from the top edge (so the top-left corner is 0,0 and the position immediately to its right is 1,0).

Your job is to figure out which asteroid would be the best place to build a new monitoring station. A monitoring station can detect any asteroid to which it has direct line of sight - that is, there cannot be another asteroid exactly between them. This line of sight can be at any angle, not just lines aligned to the grid or diagonally. The best location is the asteroid that can detect the largest number of other asteroids.

For example, consider the following map:

.#..#
.....
#####
....#
...##
The best location for a new monitoring station on this map is the highlighted asteroid at 3,4 because it can detect 8 asteroids, more than any other location. (The only asteroid it cannot detect is the one at 1,0; its view of this asteroid is blocked by the asteroid at 2,2.) All other asteroids are worse locations; they can detect 7 or fewer other asteroids. Here is the number of other asteroids a monitoring station on each asteroid could detect:

.7..7
.....
67775
....7
...87
Here is an asteroid (#) and some examples of the ways its line of sight might be blocked. If there were another asteroid at the location of a capital letter, the locations marked with the corresponding lowercase letter would be blocked and could not be detected:

#.........
...A......
...B..a...
.EDCG....a
..F.c.b...
.....c....
..efd.c.gb
.......c..
....f...c.
...e..d..c

Find the best location for a new monitoring station. How many other asteroids can be detected from that location?
 */
fun main(args: Array<String>) {
    val asteroidMap = AsteroidMap(File(ClassLoader.getSystemResource("adventofcode/day10/input.txt").file).readLines())
    val bestStationLocation = asteroidMap.bestMonitoringStationLocation()!!
    println("Part 1 Answer: Number of Asteroids in line of sight of best monitoring station location: ${ bestStationLocation.second }")
}