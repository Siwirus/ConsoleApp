import kotlin.test.assertEquals
import kotlinx.cli.*
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.pow


class Tests {
    @Test
    fun test1() {
        val arg =  arrayOf("-n", "src/main/resources/lox")
        val res = body(arg)
        assertEquals("104.9990234375 KB", res )
    }

    @Test
    fun test2() {
        val arg =  arrayOf("src/main/resources/Lugansk.txt")
        val res = body(arg)
        assertEquals("0.0078125", res )
    }
    @Test
    fun test3() {
        val arg = arrayOf("-n", "src/main/resources/Lugansk.txt")
        assertEquals("8 B", body(arg))
    }
    @Test
    fun test4() {
        val arg = arrayOf("-n", "src/main/resources/Lugansk.txt", "src/main/resources/lox")
        assertEquals("8 B, 104.9990234375 KB", body(arg))
    }
    @Test
    fun test5() {
        val arg = arrayOf("-n", "-c", "src/main/resources/Lugansk.txt", "src/main/resources/lox")
        assertEquals("105.0068359375 KB", body(arg))
    }
    @Test
    fun test6() {
        val arg = arrayOf("--si", "-n", "-c", "src/main/resources/Lugansk.txt", "src/main/resources/lox")
        assertEquals("107.527 KB", body(arg))
    }
}