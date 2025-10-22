package com.web.ncumt.controller;

import com.web.ncumt.constant.URLConstant;
import com.web.ncumt.dto.FrontendError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogController {

    @PostMapping(URLConstant.FRONTEND_LOG_ERROR)
    public void logError(@RequestBody FrontendError frontendError) {
        log.error("{}", frontendError);
    }
}
