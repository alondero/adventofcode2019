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

class DoesNotContain3OrMoreAdjacentDigitsUnlessADifferentSinglePairExists: PasswordPredicate {
    override fun verifyPassword(password: Int): Boolean {
        var adjacentDigits: MutableList<Pair<Int, Int>> = mutableListOf()
        var currDigitIdx = 0
        var currDigit = password.digitAtIndex(currDigitIdx)
        var currDigitAdjacencyLength = 1

        while (currDigit != -1) {
            if (currDigit == password.digitAtIndex(++currDigitIdx)) {
                currDigitAdjacencyLength++
            } else {
                adjacentDigits.add(currDigit to currDigitAdjacencyLength)
                currDigit = password.digitAtIndex(currDigitIdx)
                currDigitAdjacencyLength = 1
            }
        }

        adjacentDigits.add(currDigit to currDigitAdjacencyLength)

        return adjacentDigits.any { it.second == 2 } || adjacentDigits.all { it.second <= 1 }
    }
}

class AscendingDigits: PasswordPredicate {
    override fun verifyPassword(password: Int) = (0..password.toString().length-2).all { password.digitAtIndex(it) <= password.digitAtIndex(it+1) }
}