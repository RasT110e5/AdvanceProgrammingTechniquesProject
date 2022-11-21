package up.roque.drivingappointment.users

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class BaseUser {
  @Id
  @Column(name = "usr")
  open var username: String? = null
  @JsonIgnore
  open var secret: String? = null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as BaseUser

    return username != null && username == other.username
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(username = $username )"
  }
}

