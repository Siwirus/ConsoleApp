//-n: People can read the size of file
//-c: We must write a total size of all files
//-si: The base is not 1024 but 1000

import kotlinx.cli.*
import java.io.File
import kotlin.math.pow
fun main (args: Array<String>){
    println(body(args))
}

fun body(args: Array<String>): String {
    var result = ""
    val argument = parserOfArgs(args)
    var listOfAnswers = mutableListOf<Long>()
    var baseOfAnswer = 1024.0
    for (file in argument.input) {
        listOfAnswers += buildInputReader(file)
    }
    if (argument.base) baseOfAnswer = 1000.0
    val sumOfAnswers = mutableListOf<Long>() + listOfAnswers.sum()
    when {
        argument.read && argument.total -> result =  translator(sumOfAnswers, baseOfAnswer).joinToString()
        argument.read -> result = translator(listOfAnswers, baseOfAnswer).joinToString()
        !argument.read && argument.total -> result = (listOfAnswers.sum() / baseOfAnswer).toString()
        !argument.read -> result = (listOfAnswers.map { it / baseOfAnswer }).joinToString()
    }
    return result
}

class Arguments(val input: List<String>, val base: Boolean, val total: Boolean, val read: Boolean)

fun parserOfArgs(args: Array<String>): Arguments {
    val parser = ArgParser("du")
    val answerFormatOption by parser.configureAnswerFormatOption()
    val totalAnswerOption by parser.configureTotalAnswerOption()
    val baseOfAnswerOption by parser.configureBaseOfAnswerOption()
    val files by parser.argument(ArgType.String, description = "Input file").vararg()

    parser.parse(args)
    return Arguments(files, baseOfAnswerOption == true, totalAnswerOption == true, answerFormatOption == true)

}

fun translator(list: List<Long>, base: Double): Array<String> {
    val result = mutableListOf<String>()
    for (size in list) {
        when {
            size >= 1 && size < base -> result += "$size B"
            size / base >= 1 && size < base.pow(2) -> result += (size / base).toString() + " KB"
            size / base.pow(2) >= 1 && size < base.pow(3) -> result += (size / base.pow(2)).toString() + " MB"
            size / base.pow(3) >= 1 -> result += (size / base.pow(3)).toString() + " GB"
        }
    }
    return result.toTypedArray()
}

fun buildInputReader(path: String?): Long {
    val file = File(path)
    if (!file.isFile && !file.isDirectory) throw Exception("You haven't this file on your pc dude.")
    return if (!file.isDirectory) file.length()
    else file.walkTopDown().filter { it.isFile }.map { it.length() }.sum()

}

fun ArgParser.configureAnswerFormatOption(): SingleNullableOption<Boolean> {
    val description = "People can read the size of file"

    return option(ArgType.Boolean, shortName = "n", description = description)
}

fun ArgParser.configureTotalAnswerOption(): SingleNullableOption<Boolean> {
    val description = "We must write a total size of all files"

    return option(ArgType.Boolean, shortName = "c", description = description)
}

fun ArgParser.configureBaseOfAnswerOption(): SingleNullableOption<Boolean> {
    val description = "The base is not 1024 but 1000"

    return option(ArgType.Boolean, fullName = "si", description = description)
}






