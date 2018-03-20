package com.wang.blog.helper.config;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.layout.TTLLLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;


public class LogbackConfig extends BasicConfigurator implements Configurator{
    @Override
    public void configure(LoggerContext loggerContext) {
        addInfo("Setting up default configuration.");

                 ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
                  ca.setContext(loggerContext);
                  ca.setName("console");
                  LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<ILoggingEvent>();
                  encoder.setContext(loggerContext);

                  // same as
                  // PatternLayout layout = new PatternLayout();
                  // layout.setPattern("%d{HH:mm:ss.SSS} [%thread] %-level %logger{} - %msg%n");
                  TTLLLayout layout = new TTLLLayout();

                  layout.setContext(loggerContext);
                  layout.start();
                  encoder.setLayout(layout);

                  ca.setEncoder(encoder);
                  ca.start();

                  Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
                  rootLogger.addAppender(ca);

                  Logger hibernateLogger = loggerContext.getLogger("org.hibernate");
                  hibernateLogger.addAppender(ca);
                  hibernateLogger.setLevel(Level.TRACE);
    }
}
