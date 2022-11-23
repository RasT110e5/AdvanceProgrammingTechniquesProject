package up.roque.drivingappointment.exam

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
import up.roque.drivingappointment.question.Question
import up.roque.drivingappointment.question.option.Option
import up.roque.drivingappointment.user.student.Student
import javax.persistence.*

@Entity
open class ExamAttempt {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  open var id: Int? = null

  @JsonIgnore
  @ManyToMany
  @JoinTable(
    name = "exam_selected_options",
    joinColumns = [JoinColumn(name = "exam_attempt_id")],
    inverseJoinColumns = [JoinColumn(name = "options_id")]
  )
  open var options: MutableSet<Option> = mutableSetOf()

  @JsonIgnore
  @OneToOne(optional = false)
  @JoinColumn(name = "driving_test_appointment_id", nullable = false)
  open var appointment: DrivingTestAppointment? = null

  fun isApproved(): Boolean {
    return options.stream().filter { it.correct ?: false }.count() >= 8
  }

  fun addSelectedOption(option: Option) {
    val questions = getRespondedQuestions()
    if (!questions.contains(option.question))
      this.options.add(option)
  }

  @JsonIgnore
  fun getRespondedQuestions(): Set<Question?> {
    return options.map { it.question }.toSet()
  }

  @JsonProperty("student")
  fun getStudent(): Student? {
    return this.appointment?.student
  }

  fun toDto(): ExamAttemptDto {
    return ExamAttemptDto(
      this.id,
      this.options.map { it.toDto() }.toSet(),
      this.getRespondedQuestions(),
      isApproved(),
      this.appointment?.isValidNow() ?: false
    )
  }
}