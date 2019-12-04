package adventofcode2019.day4

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class ValidPasswordCounterTest {
    @Test
    fun countsNumberOfValidPasswordsWithinInputRange() {
        val passwordCounter = ValidPasswordCounter(PasswordVerifier(listOf(IsLessThan3())))
        assertThat(passwordCounter.countValidPassword(1..5), equalTo(2))
        assertThat(passwordCounter.countValidPassword(5..10), equalTo(0))
    }

    class IsLessThan3 : PasswordPredicate {
        override fun verifyPassword(password: Int) = password < 3
    }
}