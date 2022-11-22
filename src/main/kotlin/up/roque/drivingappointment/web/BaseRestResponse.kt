package up.roque.drivingappointment.web

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.aspectj.weaver.ast.Not
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

private const val SUCCESS = "Successful Request"

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseRestResponse<T>(
  val status: HttpStatus,
  val message: String,
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  val timestamp: LocalDateTime = LocalDateTime.now(),
  val content: T,
) {
  companion object {
    fun <T> ok(content: T): ResponseEntity<BaseRestResponse<T>> {
      val okResponse = BaseRestResponse(HttpStatus.OK, SUCCESS, content = content)
      return ResponseEntity.ok(okResponse)
    }

    fun ok(message: String): ResponseEntity<BaseRestResponse<Nothing?>> {
      val okResponse = BaseRestResponse(HttpStatus.OK, message, content = null)
      return ResponseEntity.ok(okResponse)
    }

    fun badRequest(message: String): ResponseEntity<BaseRestResponse<Nothing?>> {
      val badRequestResponse = BaseRestResponse(HttpStatus.BAD_REQUEST, message, content = null)
      return ResponseEntity.badRequest().body(badRequestResponse)
    }

    fun internalError(message: String): ResponseEntity<BaseRestResponse<Nothing?>> {
      val internalServerResponse = BaseRestResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, content = null)
      return ResponseEntity.internalServerError().body(internalServerResponse)
    }
  }
}
