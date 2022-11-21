package up.roque.drivingappointment.users.admin

import up.roque.drivingappointment.users.BaseUser
import javax.persistence.Entity

@Entity
open class Admin : BaseUser() {
  companion object {
    fun with(username: String, password: String): Admin {
      val user = Admin()
      user.username = username
      user.secret = password
      return user
    }
  }
}