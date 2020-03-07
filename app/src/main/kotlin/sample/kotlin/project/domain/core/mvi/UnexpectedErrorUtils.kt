package sample.kotlin.project.domain.core.mvi

import io.logging.LogSystem
import org.slf4j.Logger

fun unexpectedError(logger: Logger, throwable: Throwable) {
    logger.error("Unexpected error occurred", throwable)
    LogSystem.report(logger, "Unexpected error occurred", throwable)
}
