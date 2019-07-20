package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly
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
    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        // validating answer, don't change status if is not validated
        when (question) {
            Question.NAME -> {
                if (!answer[0].isUpperCase())
                    return "Имя должно начинаться с заглавной буквы\n${question.question}" to status.color
            }
            Question.PROFESSION -> {
                if (!answer[0].isLowerCase())
                    return "Профессия должна начинаться со строчной буквы\n${question.question}" to status.color
            }
            Question.MATERIAL -> {
                if (answer.contains(Regex("[0-9]")))
                    return "Материал не должен содержать цифр\n${question.question}" to status.color
            }
            Question.BDAY -> {
                if (!answer.isDigitsOnly())
                    return "Год моего рождения должен содержать только цифры\n${question.question}" to status.color
            }
            Question.SERIAL -> {
                if (!(answer.length == 7 && answer.isDigitsOnly()))
                    return "Серийный номер содержит только цифры, и их 7\n${question.question}" to status.color
            }
            Question.IDLE -> {
                return "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
            }
        }


        for (correctAnswer in question.answers) {
            if (answer.equals(correctAnswer, true)) {
                question = question.nextQuestion()
                return "Отлично - ты справился\n${question.question}" to status.color
            }
        }

        if (status == Status.CRITICAL) {
            status = status.nextStatus()
            question = Question.NAME
            return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        }

        status = status.nextStatus()
        return "Это неправильный ответ\n${question.question}" to status.color
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
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = NAME
        };

        abstract fun nextQuestion(): Question
    }
}