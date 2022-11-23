package up.roque.drivingappointment.exam.schedule

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import up.roque.drivingappointment.exam.ExamService

@Component
class ExamAttemptCleanerTask(
  private val examService: ExamService
) {
  private val log = LoggerFactory.getLogger(this.javaClass)

  @Scheduled
  fun cleanExamsOlderThanAWeek() {
    log.info("Starting cleaner task")
    val exams = examService.findExamsOlderThan(7)
    log.info("Found ${exams.size} exams older than 7 days to be deleted, full list='$exams'")
    examService.deleteAll(exams)
  }
}