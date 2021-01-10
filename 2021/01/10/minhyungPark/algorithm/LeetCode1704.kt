package leetcode

class LeetCode1704 {

    companion object {
        val vowels = mutableSetOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
    }

    fun halvesAreAlike(s: String)
            = s.substring(0, s.length/2).getVowelsCount() == s.substring(s.length/2, s.length).getVowelsCount()


    private fun String.getVowelsCount()
            = this.filter { vowels.contains(it) }.length
}