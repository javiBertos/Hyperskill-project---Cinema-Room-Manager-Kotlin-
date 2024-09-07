package cinema

class Cinema {
    private val rows: Int = 7
    private val seats: Int = 8
    private val booked: List<List<Int>> = emptyList()

    private fun getCellContent(row: Int, seat: Int): Char {
        return when {
            row > 0 && seat == 0 -> row.toString().first()
            row == 0 && seat > 0 -> seat.toString().first()
            row > 0 && seat > 0 -> if (listOf(row, seat) in booked) 'X' else 'S'
            else -> ' '
        }
    }

    fun printRoom(): Unit {
        println("Cinema:")

        for (i in 0..this.rows) {
            for (j in 0..this.seats) {
                print("${if (j == 0) "" else " " }${this.getCellContent(i, j)}")
            }
            println()
        }
    }
}

fun main() {
    // write your code here
    val myCinema = Cinema()

    myCinema.printRoom()
}