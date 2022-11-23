package up.roque.drivingappointment.question

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import up.roque.drivingappointment.question.dto.QuestionDto
import up.roque.drivingappointment.question.option.Option
import java.util.stream.Collectors
import javax.persistence.*

@Entity
open class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  open var id: Int? = null
  open var text: String? = null

  @JsonIgnore
  @OneToMany(mappedBy = "question")
  open var options: MutableList<Option> = mutableListOf()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
    other as Question

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(id = $id , text = $text )"
  }

  fun toDto(): QuestionDto {
    val questionDto = QuestionDto(this.text!!, this.options.stream().map { it.toDto() }.collect(Collectors.toList()))
    questionDto.id = this.id
    return questionDto
  }

}