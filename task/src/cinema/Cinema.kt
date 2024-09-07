package cinema

class Cinema {
    private var rows: Int = 7
        set(value) {
            field = if (value >= 1) value else {
                println("No less than 1 rows is possible... Setting to 3")
                1
            }

            this.roomSeats = field * this.seatsPerRow
        }
    private var seatsPerRow: Int = 8
        set(value) {
            field = if (value >= 1) value else {
                println("No less than 1 seats per row is possible... Setting to 4")
                1
            }

            this.roomSeats = field * this.rows
        }
    private var roomSeats: Int = rows * seatsPerRow
    private val seatPrice: Int = 10
    private val seatPriceReduced: Int = 8
    private val booked: List<List<Int>> = emptyList()

    private fun getCellContent(row: Int, seat: Int): Char {
        return when {
            row > 0 && seat == 0 -> row.toString().first()
            row == 0 && seat > 0 -> seat.toString().first()
            row > 0 && seat > 0 -> if (listOf(row, seat) in booked) 'X' else 'S'
            else -> ' '
        }
    }

    fun printRoom() {
        println("Cinema:")

        for (i in 0..this.rows) {
            for (j in 0..this.seatsPerRow) {
                print("${if (j == 0) "" else " " }${this.getCellContent(i, j)}")
            }
            println()
        }
    }

    fun customiseRoomSize() {
        println("Enter the number of rows:")
        this.rows = readln().toInt()

        println("Enter the number of seats in each row:")
        this.seatsPerRow = readln().toInt()
    }

    fun getMaxIncome(): Int {
        return if (this.roomSeats <= 60) {
            this.roomSeats * this.seatPrice
        } else {
            val roomExpHalf: Int = this.rows / 2
            val roomCheapHalf: Int = this.rows - roomExpHalf

            (roomExpHalf * this.seatsPerRow * seatPrice) +
                    (roomCheapHalf * this.seatsPerRow * seatPriceReduced)
        }
    }
}

fun main() {
    // write your code here
    val myCinema = Cinema()

    myCinema.customiseRoomSize()

    println("Total income:")
    println("$${myCinema.getMaxIncome()}")
}