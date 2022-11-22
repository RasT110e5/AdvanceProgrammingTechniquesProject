package up.roque.drivingappointment.user.student

import org.hibernate.Hibernate
import up.roque.drivingappointment.appointment.Appointment
import up.roque.drivingappointment.user.BaseUser
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
open class Student : BaseUser() {
  open var attempts: Int? = 0

  @OneToMany(mappedBy = "student")
  open var appointments: MutableSet<Appointment> = mutableSetOf()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as Student

    return username != null && username == other.username
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(username = $username )"
  }

  companion object {
    fun with(username: String, password: String): Student {
      val student = Student()
      student.username = username
      student.secret = password
      return student
    }
  }
}