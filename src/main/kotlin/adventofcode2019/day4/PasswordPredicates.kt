package adventofcode2019.day4

import kotlin.math.abs
import kotlin.math.log10

interface PasswordPredicate {
    fun verifyPassword(password: Int): Boolean
}

class LengthOfPassword: PasswordPredicate {
    override fun verifyPassword(password: Int) = log10(abs(password.toDouble())).toInt() + 1 == 6
}

class ContainsAdjacentPairOfDigits: PasswordPredicate {
    override fun verifyPassword(password: Int) = (0..password.toString().length-2).any { password.digitAtIndex(it) == password.digitAtIndex(it+1) }
}

class AscendingDigits: PasswordPredicate {
    override fun verifyPassword(password: Int) = (0..password.toString().length-2).all { password.digitAtIndex(it) <= password.digitAtIndex(it+1) }
}