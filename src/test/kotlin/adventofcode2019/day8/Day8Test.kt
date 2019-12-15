package adventofcode2019.day8

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class Day8Test {
    @Test
    fun `parses input into image layers`() {
        assertThat(fromSIF("01011111", width = 2, height = 2).toString(), equalTo("[[01, 01], [11, 11]]"))
        assertThat(fromSIF("123456789012", width = 3, height = 2).toString(), equalTo("[[123, 456], [789, 012]]"))
    }

    @Test
    fun `finds layer with least occurrences of specified number`() {
        val pic = fromSIF("112211121222", width = 1, height = 4)
        assertThat(pic.layerWithLeastOccurrencesOf(1)?.index, equalTo(2))
    }
}