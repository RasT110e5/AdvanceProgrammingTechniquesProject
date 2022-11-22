package up.roque.drivingappointment.appointment.eye

import org.hibernate.Hibernate
import up.roque.drivingappointment.appointment.Appointment
import javax.persistence.Entity
import javax.persistence.Table
import kotlin.random.Random.Default.nextInt

@Entity
open class EyeAppointment : Appointment() {
  open var approved: Boolean = false
  open var doctorName: String? = null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as EyeAppointment

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(approved = $approved , doctorName = $doctorName, appointment= ${super.toString()} )"
  }
}