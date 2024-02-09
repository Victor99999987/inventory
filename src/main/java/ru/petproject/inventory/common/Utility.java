package ru.petproject.inventory.common;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class Utility {
    public static void logEndpoint(Logger log, HttpServletRequest request){
        log.info("Получен запрос {} {}", request.getMethod(), request.getRequestURI());
    }

}
