import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads the whole text from the given input txt file.
 */
fun readTextInput(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

operator fun String.component1() = split(" ")[0]
operator fun String.component2() = split(" ")[1]
operator fun String.component3() = split(" ")[2]

data class Point(val x: Int, val y: Int)