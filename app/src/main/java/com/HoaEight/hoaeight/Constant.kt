package com.HoaEight.hoaeight

object Constant {
    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val q1 = Question(
            1,
            "Siya ang kilala bilang Bossing ng eat bulaga. ",
            R.drawable.vic,
            "Vic Sotto",
            "Vice Ganda",
            "Malupiton",
            "Wally",
            1
        )

        val q2 = Question(
            2,
            "Sino ang kilalang 'Bitoy' sa GMA?",
            R.drawable.michael_v,
            "Wally Bayola",
            "Jose Manalo",
            "Michael V.",
            "Allan K.",
            3
        )

        val q3 = Question(
            3,
            "Siya ang kilalang mahabang baba",
            R.drawable.babalu,
            "Vic Sotto",
            "Joey de Leon",
            "Babalu",
            "Paolo Ballesteros",
            3
        )

        val q4 = Question(
            4,
            "Sino ang komedyanteng laging may 'banat' sa pelikula ni Vice Ganda?",
            R.drawable.vhong,
            "Vhong Navarro",
            "Teddy Corpuz",
            "Vice Ganda",
            "Jhong Hilario",
            1
        )

        val q5 = Question(
            5,
            "Sino ang komedyanteng kilala bilang 'Chokoleit'?",
            R.drawable.chokoleit,
            "Jonathan Garcia",
            "Pekto",
            "Sweet Lapus",
            "Pokwang",
            1
        )

        val q6 = Question(
            6,
            "Sino ang babaeng komedyante na madalas kasama ni Ai Ai delas Alas?",
            R.drawable.pokwang,
            "Pokwang",
            "K Brosas",
            "Rufa Mae Quinto",
            "Eugene Domingo",
            1
        )

        val q7 = Question(
            7,
            "Kilala bilang isa sa mga main host ng bayanihan EAT",
            R.drawable.jose_manalo,
            "K Brosas",
            "Pokwang",
            "Allan K.",
            "Jose Manalo",
            4
        )

        val q8 = Question(
            8,
            "Sino ang 'Vice' sa pelikulang 'The Mall, The Merrier'?",
            R.drawable.vice_ganda,
            "Vhong Navarro",
            "Vice Ganda",
            "Ogie Diaz",
            "Chokoleit",
            2
        )

        val q9 = Question(
            9,
            "Siya ang laging kasama at tandem ni Jose sa eat bulaga.",
            R.drawable.wally_bayola,
            "Luis Manzano",
            "Wally Bayola",
            "Ogie Diaz",
            "Enchong Dee",
            2
        )

        val q10 = Question(
            10,
            "kilalang komedyante sa showtime na miyembro ng street boys?",
            R.drawable.jhong,
            "Vice Ganda",
            "Jhong Hillario",
            "Boy Abunda",
            "Tito Sotto",
            2
        )

        questionsList.addAll(listOf(q1, q2, q3, q4, q5, q6, q7, q8, q9, q10))
        questionsList.shuffle()

        for (i in questionsList.indices) {
            val question = questionsList[i]
            val options = listOf(
                question.optionOne,
                question.optionTwo,
                question.optionThree,
                question.optionFour
            ).shuffled()

            val correctAnswerText = when (question.correctAnswer) {
                1 -> question.optionOne
                2 -> question.optionTwo
                3 -> question.optionThree
                4 -> question.optionFour
                else -> question.optionOne
            }
            
            val newCorrectIndex = options.indexOf(correctAnswerText) + 1
            questionsList[i] = Question(
                question.id,
                question.question,
                question.image,
                options[0],
                options[1],
                options[2],
                options[3],
                newCorrectIndex
            )
        }

        return questionsList
    }
}