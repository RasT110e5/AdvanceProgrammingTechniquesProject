package up.roque.drivingappointment.user.admin

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import up.roque.drivingappointment.question.ModifyQuestionDto
import up.roque.drivingappointment.question.Question
import up.roque.drivingappointment.question.QuestionDto
import up.roque.drivingappointment.question.QuestionRepository
import up.roque.drivingappointment.question.option.ModifyOptionDto
import up.roque.drivingappointment.question.option.Option
import up.roque.drivingappointment.question.option.OptionDto
import up.roque.drivingappointment.question.option.OptionRepository
import java.lang.RuntimeException

@Service
@Transactional(readOnly = true)
class QuestionService(
  private val questionRepository: QuestionRepository,
  private val optionRepository: OptionRepository
) {

  @Transactional
  fun createQuestion(questionDto: QuestionDto): Question {
    val question = questionDto.toEntity()
    questionRepository.save(question)
    for (optionDto in questionDto.options) createOption(optionDto, question)
    return question
  }

  @Transactional
  fun createOption(optionDto: OptionDto, question: Question): Option {
    val option = optionDto.toEntity()
    option.question = question
    optionRepository.save(option)
    return option
  }

  fun findAllQuestions(): List<Question> {
    return questionRepository.findAll()
  }

  @Transactional
  fun modifyQuestion(modifyDto: ModifyQuestionDto): Question {
    val question = getQuestion(modifyDto.id)
    question.text = modifyDto.newText
    return questionRepository.save(question)
  }

  fun getQuestion(id: Int): Question {
    return questionRepository.findById(id).orElseThrow { NoQuestionForId(id) }
  }

  fun findAllOptions(): List<Option> {
    return optionRepository.findAll()
  }

  fun getOption(id: Int): Option {
    return optionRepository.findById(id).orElseThrow { NoOptionForId(id) }
  }

  @Transactional
  fun modifyOption(modifyDto: ModifyOptionDto): Option {
    val question = getQuestion(modifyDto.questionId)
    val option = getOption(modifyDto.id)
    option.text = modifyDto.newText
    option.correct = modifyDto.correct
    option.question = question
    return optionRepository.save(option)
  }

  class NoOptionForId(id: Int) : RuntimeException("No option found for id:$id")
  class NoQuestionForId(id: Int) : RuntimeException("No question found for id:$id")
}
