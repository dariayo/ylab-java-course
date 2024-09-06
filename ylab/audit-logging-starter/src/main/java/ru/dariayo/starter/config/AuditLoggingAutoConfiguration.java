package ru.dariayo.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dariayo.starter.audit.AuditAspect;
import ru.dariayo.starter.logging.LoggingAspect;

@Configuration
public class AuditLoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }
}
