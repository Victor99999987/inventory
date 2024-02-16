package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
    private final HttpServletRequest request;

    public void logEndpoint() {
        log.info("Получен запрос {} {}", request.getMethod(), request.getRequestURI());
    }

    public void logException(Throwable e) {
        log.warn("Ошибка {} \n {}", e.getMessage(), e.getStackTrace());
    }

    public void logException(HttpStatus status, Throwable e) {
        log.warn("Получен статус {} {} \n {}", status.value(), e.getMessage(), e.getStackTrace());
    }
}
