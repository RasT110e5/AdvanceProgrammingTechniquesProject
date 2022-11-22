package up.roque.drivingappointment.appointment.drivingtest

import org.hibernate.Hibernate
import up.roque.drivingappointment.appointment.Appointment
import up.roque.drivingappointment.user.student.Student
import java.util.*
import javax.persistence.Entity

@Entity
open class DrivingTestAppointment : Appointment() {
  open var secretKey: UUID = UUID.randomUUID()
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as DrivingTestAppointment

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  fun reserve(student: Student) {
    super.student = student
    super.available = false
  }

  fun free(student: Student) {
    super.student = student
    super.available = false
  }

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(secretKey = $secretKey, appointment= ${super.toString()} )"
  }
}