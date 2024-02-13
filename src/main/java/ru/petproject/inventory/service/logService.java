package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class logService {
    private final HttpServletRequest request;

    public void logEndpoint() {
        log.info("Получен запрос {} {}", request.getMethod(), request.getRequestURI());
    }

    public void logException(String message) {
        log.warn(message);
    }
}
