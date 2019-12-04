package adventofcode2019.day4

class PasswordVerifier(private val rules: List<PasswordPredicate>) {
    fun verifyPassword(input: Int) = rules.all { it.verifyPassword(input) }
}

fun Int.digitAtIndex(idx: Int) = toString()[idx].toInt()