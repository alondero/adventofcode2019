package adventofcode2019.day3

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day3Test {

    @ParameterizedTest(name = "wireA: {0}, wireB: {1} = manhattan distance: {2}")
    @CsvSource(
        "'U2,R2','R1,U2', 3",
        "'R75,D30,R83,U83,L12,D49,R71,U7,L72','U62,R66,U55,R34,D71,R55,D58,R83', 159"
    )
    fun findsManhattanDistanceFromIntersection(wireA: String, wireB: String, expectedDistance: Int) {
        assertThat(calculateClosestIntersection(wireA, wireB), equalTo(expectedDistance))
    }

    @ParameterizedTest(name = "wireA: {0}, wireB: {1} = signal delay: {2}")
    @CsvSource(
        "'U2,R2','R1,U2',6",
        "'R75,D30,R83,U83,L12,D49,R71,U7,L72','U62,R66,U55,R34,D71,R55,D58,R83', 610",
        "'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51','U98,R91,D20,R16,D67,R40,U7,R15,U6,R7', 410"
    )
    fun findsLeastWireStepsFromIntersection(wireA: String, wireB: String, expectedSteps: Int) {
        assertThat(calculateShortestSignalDelayIntersection(wireA, wireB), equalTo(expectedSteps))
    }
}