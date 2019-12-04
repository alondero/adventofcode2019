package adventofcode2019.day4

class PasswordVerifier(private val rules: List<PasswordPredicate>) {
    fun verifyPassword(input: Int) = rules.all { it.verifyPassword(input) }
}

fun Int.digitAtIndex(idx: Int) = if (idx < toString().length) {this.toString().substring(idx, idx+1).toInt()} else -1