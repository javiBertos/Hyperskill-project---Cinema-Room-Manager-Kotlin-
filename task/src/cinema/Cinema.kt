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
    private val booked: MutableList<List<Int>> = mutableListOf()
    private var bookedIncome: Int = 0
    private val seatFree: Char = 'S'
    private val seatBooked: Char = 'B'

    private fun getCellContent(row: Int, seat: Int): Char {
        return when {
            row > 0 && seat == 0 -> row.toString().first()
            row == 0 && seat > 0 -> seat.toString().first()
            row > 0 && seat > 0 -> if (listOf(row, seat) in booked) this.seatBooked else this.seatFree
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

    private fun requestSiteCords(): List<Int> {
        println("Enter a row number:")
        val seatRow = readln().toInt()

        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()

        if (seatRow < 1 || seatRow > this.rows || seatNumber < 1 || seatNumber > this.seatsPerRow) {
            throw Exception("\nWrong input!\n")
        }

        val seatLocation = listOf(seatRow, seatNumber)

        if (this.booked.indexOf(seatLocation) != -1) {
            throw Exception("\nThat ticket has already been purchased!\n")
        }

        return seatLocation
    }

    fun bookASeat(): Int {
        var seatLocation = emptyList<Int>()
        var isValidSeat = true

        do {
            try {
                seatLocation = this.requestSiteCords()
                isValidSeat = true
            } catch (e: Exception) {
                isValidSeat = false
                println(e.message)
            }
        } while (!isValidSeat)

        val seatPrice = this.getSitePrice(seatLocation.first())

        this.booked.add(seatLocation)
        this.bookedIncome += seatPrice

        return seatPrice
    }

    private fun getSitePrice(row: Int): Int {
        return if (this.roomSeats <= 60) {
            this.seatPrice
        } else {
            if (row in 1..this.rows / 2) {
                this.seatPrice
            } else {
                this.seatPriceReduced
            }
        }
    }

    fun getMaxIncome(): Int {
        var maxIncome: Int = 0

        for (row in 1..this.rows) {
            maxIncome += this.seatsPerRow * this.getSitePrice(row)
        }

        return maxIncome
    }

    fun showStatistics() {
        println(
            """
                Number of purchased tickets: ${this.booked.size}
                Percentage: ${"%.2f".format(this.booked.size.toDouble() / this.roomSeats.toDouble() * 100.00)}%
                Current income: $${this.bookedIncome}
                Total income: $${this.getMaxIncome()}
            """.trimIndent()
        )
    }
}

fun main() {
    // write your code here
    val myCinema = Cinema()

    myCinema.customiseRoomSize()

    var userOption: Int? = null

    do {
        println(
            """
                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                
            """.trimIndent()
        )
        userOption = readln().toIntOrNull()

        when(userOption) {
            1 -> myCinema.printRoom()
            2 -> println("\nTicket price: \$${myCinema.bookASeat()}\n")
            3 -> myCinema.showStatistics()
            0 -> {}
            else -> println("Wrong input!")
        }
    } while (userOption != 0)
}