package adventofcode2019.day6

import java.io.File

/*
--- Day 6: Universal Orbit Map ---
You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa. You download a map of the local orbits (your puzzle input).

Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object. An orbit looks roughly like this:

                  \
                   \
                    |
                    |
AAA--> o            o <--BBB
                    |
                    |
                   /
                  /
In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".

Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits (like the one shown above) and indirect orbits.

Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
Visually, the above map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I
In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.

Here, we can count the total number of orbits as follows:

D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
COM orbits nothing.
The total number of direct and indirect orbits in this example is 42.

What is the total number of direct and indirect orbits in your map data?
 */
typealias SpaceTransfer = Pair<SpaceObject, SpaceObject>

fun orbitalSystemFrom(systemMap: String) = systemMap.lines() // Any reason why .lines() struggles with type inference??
    .flatMap { it.split(")") }
    .chunked(2)
    .fold(OrbitalSystem()) { system, (parent, objectThatOrbits) ->
        system.apply {
            (system.find(parent) ?: (SpaceObject(parent).apply { potentialCentreOfMasses += this }))
                .apply {
                    val orbiter = system.find(objectThatOrbits)?.also { it.parent = this } ?: SpaceObject(objectThatOrbits, this)
                    addOrbit(orbiter)
                }
        }
    }

class OrbitalSystem {
    var potentialCentreOfMasses: List<SpaceObject> = mutableListOf()

    fun find(spaceObjectName: String) = potentialCentreOfMasses.mapNotNull { it.find(spaceObjectName) }.firstOrNull()

    fun numberOfOrbits() = potentialCentreOfMasses.firstOrNull { it.parent == null }
        ?.let { it.numberOfOrbits(0) } ?: 0

    fun orbitalTransfersBetween(from: String, to: String) = find(from)?.parent?.let { fromObject ->
        find(to)?.parent?.let { toObject ->
            fromObject.pathBetween(toObject)
        }
    }
}

data class SpaceObject(val name: String, var parent: SpaceObject? = null) {
    private val objectsThatOrbit: MutableList<SpaceObject> = mutableListOf()

    fun find(spaceObjectName: String): SpaceObject? = when (spaceObjectName) {
        name -> this
        else -> objectsThatOrbit.mapNotNull { it.find(spaceObjectName) }.firstOrNull()
    }

    fun pathBetween(spaceObject: SpaceObject): List<SpaceTransfer>? {
        val pathBetweenObjects = mutableListOf<SpaceTransfer>()
        var commonObject = this
        var previousObject: SpaceObject? = null

        // Go up
        while (commonObject.find(spaceObject.name) == null) {
            if (previousObject != null) {
                pathBetweenObjects += previousObject to commonObject
            }
            previousObject = commonObject
            commonObject = commonObject.parent!!
        }

        if (previousObject != null) {
            pathBetweenObjects += previousObject to commonObject
        }

        // Then back down
        return pathBetweenObjects.apply {
            addAll(
                commonObject.objectsThatOrbit.filter { it.find(spaceObject.name) != null }.fold(arrayListOf()) { path, objectThatOrbits ->
                    path.apply {
                        add(commonObject to objectThatOrbits)
                        addAll(objectThatOrbits.pathBetween(spaceObject) ?: listOf())
                    }
                })
        }
    }


    fun addOrbit(orbitObject: SpaceObject) = this.apply {
        objectsThatOrbit += orbitObject
    }

    fun numberOfOrbits(depth: Int): Int = objectsThatOrbit.fold(depth) { count, orbit ->
        count + orbit.numberOfOrbits(depth + 1)
    }
}

/*
--- Part Two ---
Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).

You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer lets you move from any object to an object orbiting or orbited by that object.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN
Visually, the above map of orbits looks like this:

                          YOU
                         /
        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4 orbital transfers are required:

K to J
J to E
E to D
D to I
Afterward, the map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
                 \
                  YOU
What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)
 */

fun main(args: Array<String>) {
    val orbitalSystem = orbitalSystemFrom(File(ClassLoader.getSystemResource("adventofcode/day6/input.txt").file).readText())

    println("Part 1 Answer: Number of Indirect and Direct Orbits: ${orbitalSystem.numberOfOrbits()}")
    println("Part 2 Answer: Number of orbital transfers: ${orbitalSystem.orbitalTransfersBetween("YOU", "SAN")!!.size}")
}