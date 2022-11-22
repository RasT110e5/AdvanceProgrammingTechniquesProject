package up.roque.drivingappointment.user.admin

import up.roque.drivingappointment.user.BaseUser
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