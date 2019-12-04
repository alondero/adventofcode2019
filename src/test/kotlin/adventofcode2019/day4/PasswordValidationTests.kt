package adventofcode2019.day4

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PasswordValidationTests {

    @Nested
    class IndividualPasswordPredicateTests {
        @ParameterizedTest(name = "input: {0}, isPassword: {1}")
        @CsvSource(
            "55555,false",
            "666666,true",
            "7777777,false"
        )
        fun `passwords must be six digits long`(input: Int, isPassword: Boolean) {
            assertThat(LengthOfPassword().verifyPassword(input), equalTo(isPassword))
        }

        @ParameterizedTest(name = "input: {0}, isPassword: {1}")
        @CsvSource(
            "123456,false",
            "122345,true"
        )
        fun `must contain at least one pair of same adjacent digits`(input: Int, isPassword: Boolean) {
            assertThat(ContainsAdjacentPairOfDigits().verifyPassword(input), equalTo(isPassword))
        }

        @ParameterizedTest(name = "input: {0}, isPassword: {1}")
        @CsvSource(
            "111444,false",
            "112233,true",
            "111123,false"
        )
        fun `must not contain more than 3 adjacent digits`(input: Int, isPassword: Boolean) {
            assertThat(ContainsAdjacentPairOfDigits().verifyPassword(input), equalTo(isPassword))
        }

        @ParameterizedTest(name = "input: {0}, isPassword: {1}")
        @CsvSource(
            "111444,false",
            "111111122,true",
            "11122211,true",
            "1112,false",
            "111211,true",
            "123444,false",
            "111122,true"
        )
        fun `can contain more than 3 adjacent digits if a single pair of adjacent digits exists`(input: Int, isPassword: Boolean) {
            assertThat(DoesNotContain3OrMoreAdjacentDigitsUnlessADifferentSinglePairExists().verifyPassword(input), equalTo(isPassword))
        }

        @ParameterizedTest(name = "input: {0}, isPassword: {1}")
        @CsvSource(
            "998877,false",
            "112233,true"
        )
        fun `digits in ascending order`(input: Int, isPassword: Boolean) {
            assertThat(AscendingDigits().verifyPassword(input), equalTo(isPassword))
        }
    }

    @ParameterizedTest(name = "input: {0}, isPassword: {1}")
    @CsvSource(
        "111111,true",
        "223450,false",
        "123789,false"
    )
    fun `adheresToAllRules`(input: Int, isPassword: Boolean) {
        assertThat(PasswordVerifier(listOf(LengthOfPassword(), ContainsAdjacentPairOfDigits(), AscendingDigits())).verifyPassword(input), equalTo(isPassword))
    }

    @ParameterizedTest(name = "input: {0}, isPassword: {1}")
    @CsvSource(
        "112233,true",
        "123444,false",
        "111122,true"
    )
    fun `adheresToAllRulesPart2`(input: Int, isPassword: Boolean) {
        assertThat(PasswordVerifier(listOf(LengthOfPassword(), ContainsAdjacentPairOfDigits(), DoesNotContain3OrMoreAdjacentDigitsUnlessADifferentSinglePairExists(), AscendingDigits())).verifyPassword(input), equalTo(isPassword))
    }
}