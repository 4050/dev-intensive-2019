package ru.skillbranch.devintensive.models

import java.util.*


class Bender (
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>> {
        val validationResult = validateAnswer(answer)
        return when {
            !validationResult.first -> "${validationResult.second}\n${question.question}" to status.color
            question.answers.contains(answer.toLowerCase()) -> {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            }
            else -> {
                status = status.nextStatus()
                val restartBenderMessage = if(status == Status.NORMAL) ". Давай все по новой" else ""
                "Это неправильный ответ$restartBenderMessage\n${question.question}" to status.color
            }
        }
    }

    private fun validateAnswer(answer: String): Pair<Boolean, String> {
        return when {
            question == Question.NAME && answer.substring(0, 1).toUpperCase() != answer.substring(0, 1) -> false to "Имя должно начинаться с заглавной буквы"
            question == Question.PROFESSION && answer.substring(0, 1).toLowerCase() != answer.substring(0, 1) -> false to "Профессия должна начинаться со строчной буквы"
            question == Question.MATERIAL && answer.contains(Regex("""\d""")) -> false to "Материал не должен содержать цифр"
            question == Question.BDAY && answer.contains(Regex("""^\D""")) -> false to "Год моего рождения должен содержать только цифры"
            question == Question.SERIAL && !(answer.length == 7 && answer.contains(Regex("""^\d"""))) -> false to "Серийный номер содержит только цифры, и их 7"
            else -> true to ""
        }
    }


    enum class Status(val color:Triple<Int, Int, Int>){
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextStatus():Status = if (this.ordinal<values().lastIndex)  values()[this.ordinal+1] else values()[0]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальшик", "bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}