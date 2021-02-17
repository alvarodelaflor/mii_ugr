# **LOGS**

Because we are using *PLAY FRAMEWORK* as a framework we can use some of its advantages, like the integrated use of LOGS services.

**SLF4J**

By default, *PLAY FRAMEWORK* uses *SLF4J* (supported by [Logback](http://logback.qos.ch/)).

1. Log levels

Log levels are used to classify the severity of log messages. When you write a log request statement you will specify the severity and this will appear in generated log messages.

This is the set of available log levels, in decreasing order of severity.

    OFF - Used to turn off logging, not as a message classification.
    ERROR - Runtime errors, or unexpected conditions.
    WARN - Use of deprecated APIs, poor use of API, ‘almost’ errors, other runtime situations that are undesirable or unexpected, but not necessarily “wrong”.
    INFO - Interesting runtime events such as application startup and shutdown.
    DEBUG - Detailed information on the flow through the system.
    TRACE - Most detailed information.

In addition to classifying messages, log levels are used to configure severity thresholds on loggers and appenders. For example, a logger set to level INFO will log any request of level INFO or higher (INFO, WARN, ERROR) but will ignore requests of lower severities (DEBUG, TRACE).

2. Appenders

The logging API allows logging requests to print to one or many output destinations called “appenders.” Appenders are specified in configuration and options exist for the console, files, databases, and other outputs.

Appenders combined with loggers can help you route and filter log messages. For example, you could use one appender for a logger that logs useful data for analytics and another appender for errors that is monitored by an operations team.

Throughout the development of the project, logs will be used to record the status of the system, including facilitating the resolution of any problems that may arise.

3. Use

A common strategy for logging application events is to use a distinct logger per class using the class name. The logging API supports this with a factory method that takes a class argument:

        val logger: Logger = Logger(this.getClass())

Once you have a Logger set up, you can use it to write log statements:

        // Log some debug info
        logger.debug("Attempting risky calculation.")

        try {
        val result = riskyCalculation

        // Log result if successful
        logger.debug(s"Result=$result")
        } catch {
        case t: Throwable => {
        // Log error with message and Throwable.
        logger.error("Exception with riskyCalculation", t)
        }

***