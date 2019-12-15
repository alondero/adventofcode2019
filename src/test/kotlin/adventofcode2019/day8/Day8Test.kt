package adventofcode2019.day8

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class Day8Test {
    @Test
    fun `parses input into image layers`() {
        assertThat(fromSIF("01011111", width = 2, height = 2).toString(), equalTo("[[01, 01], [11, 11]]"))
    }
}