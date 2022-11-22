package up.roque.drivingappointment.exam

import com.fasterxml.jackson.annotation.JsonIgnore
import up.roque.drivingappointment.appointment.drivingtest.DrivingTestAppointment
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
    this.options.add(option)
  }

  fun getStudent(): Student? {
    return this.appointment?.student
  }
}