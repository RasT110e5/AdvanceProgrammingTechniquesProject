package up.roque.drivingappointment.exam.dto

data class ExamStatisticsDto(
  val examsTaken: Int,
  val approvedExams: Int,
  val failedExams: Int,
  val absentStudents: Int,
)