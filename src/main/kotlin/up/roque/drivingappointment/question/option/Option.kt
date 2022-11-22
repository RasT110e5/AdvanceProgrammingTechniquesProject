package up.roque.drivingappointment.question.option

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import up.roque.drivingappointment.question.Question
import java.util.IdentityHashMap
import javax.persistence.*

@Entity
open class Option {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  open var id: Int? = null
  open var text: String? = null
  open var correct: Boolean? = false

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(name = "question_id", nullable = false)
  open var question: Question? = null

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as Option

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(id = $id , text = $text , correct = $correct )"
  }

  fun toDto(): OptionDto {
    val optionDto = OptionDto(this.text!!, this.correct!!)
    optionDto.id = this.id
    return optionDto
  }
}
