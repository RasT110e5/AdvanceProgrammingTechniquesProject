package up.roque.drivingappointment.appointment

import org.hibernate.Hibernate
import up.roque.drivingappointment.user.student.Student
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  open var id: Int? = null
  open var time: LocalDateTime? = null
  open var available: Boolean = true
  open var studentAbsent: Boolean = false

  @ManyToOne
  @JoinColumn(name = "student_usr")
  open var student: Student? = null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as Appointment

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  fun wasStudenAbsent(): Boolean = studentAbsent

  fun isAvailable(): Boolean = available

  fun isValidNow(): Boolean {
    val startTime = time ?: throw TimeNotSetForAppointment()
    val now = LocalDateTime.now()
    return now.isAfter(startTime) && now.isBefore(startTime.plusHours(1))
  }

  class TimeNotSetForAppointment : RuntimeException()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(id = $id , time = $time , available = $available , studentAbsent = $studentAbsent )"
  }
}