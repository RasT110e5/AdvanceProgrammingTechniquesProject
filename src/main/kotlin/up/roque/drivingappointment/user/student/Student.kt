package up.roque.drivingappointment.user.student

import org.hibernate.Hibernate
import up.roque.drivingappointment.user.BaseUser
import javax.persistence.Entity

@Entity
open class Student : BaseUser() {
  open var attempts: Int? = 0

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