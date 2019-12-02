package adventofcode2019.day2

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day2Test {
    @ParameterizedTest(name = "input: {0}, \noutput: {1}")
    @CsvSource(
        "'1,0,0,0,99','2,0,0,0,99'",
        "'2,3,0,3,99','2,3,0,6,99'",
        "'2,4,4,5,99,0','2,4,4,5,99,9801'",
        "'1,1,1,4,99,5,6,0,99','30,1,1,4,2,5,6,0,99'"
    )
    fun `Executes from memory state`(inputMemoryState: String, outputMemoryState: String) {
        assertThat(execute(inputMemoryState), equalTo(outputMemoryState))
    }
}