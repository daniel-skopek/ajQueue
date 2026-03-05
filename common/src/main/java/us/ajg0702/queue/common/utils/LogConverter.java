package us.ajg0702.queue.common.utils;

import org.jetbrains.annotations.NotNull;
import us.ajg0702.queue.api.util.QueueLogger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogConverter extends Logger {
    private final QueueLogger logger;
    public LogConverter(QueueLogger logger) {
        super("ajqueue-convert", null);
        this.logger = logger;
    }

    @Override
    public void log(@NotNull LogRecord logRecord) {
        String message = logRecord.getMessage();
        Throwable thrown = logRecord.getThrown();
        switch(logRecord.getLevel().toString()) {
            case "OFF":
                break;
            case "SEVERE":
                if (thrown != null) {
                    logger.error(message, thrown);
                } else {
                    logger.error(message);
                }
                break;
            case "WARNING":
                if(thrown != null) {
                    logger.warn(message, thrown);
                } else {
                    logger.warn(message);
                }
                break;
            case "INFO":
            default:
                if (thrown != null) {
                    logger.info(message, thrown);
                } else {
                    logger.info(message);
                }
                break;
        }
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        LogRecord lr = new LogRecord(level, msg);
        lr.setThrown(thrown);
        log(lr);
    }

    @SuppressWarnings({"unused", "SameReturnValue"})
    public String logName() {
        return "%%__USER__%%";
    }
}
