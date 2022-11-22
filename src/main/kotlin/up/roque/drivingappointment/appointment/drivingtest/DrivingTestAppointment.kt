package up.roque.drivingappointment.appointment.drivingtest

import org.hibernate.Hibernate
import up.roque.drivingappointment.appointment.Appointment
import up.roque.drivingappointment.appointment.dto.ReservedDrivingTestAppointment
import up.roque.drivingappointment.user.student.Student
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity

@Entity
open class DrivingTestAppointment : Appointment() {
  @Column(length = 16)
  open var secretKey: UUID = UUID.randomUUID()
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as DrivingTestAppointment

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String =
    this::class.simpleName + "(secretKey = $secretKey)"

  fun toReservedDto(): ReservedDrivingTestAppointment {
    return ReservedDrivingTestAppointment(this.id, this.secretKey, this.time, this.available)
  }
}