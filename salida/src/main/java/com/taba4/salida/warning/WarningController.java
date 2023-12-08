package com.taba4.salida.warning;

import com.taba4.salida.warning.dto.EqkDto;
import com.taba4.salida.warning.dto.WarnDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/warn")
class WarningController {

    private final SseEmitters sseEmitters;
    private static final Long TIMEOUT = 120L * 1000;

    WarningController(SseEmitters sseEmitters) {

        this.sseEmitters = sseEmitters;
    }

    @GetMapping(value = "/connect", produces = "text/event-stream")
    public SseEmitter connect() throws IOException {                   //++ ResponseEntity 사용하기
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        return sseEmitters.connect(emitter);
    }

    @PostMapping(path = "/eqk")
    public void warnEqk(@RequestBody EqkDto eqkData) {
        log.info("new eqk data request");
        log.info("data = {}, {}, {}", eqkData.getLatitude(), eqkData.getLongitude(), eqkData.getMagnitude());
        sseEmitters.warnEqk(eqkData);
    }

    @PostMapping(path = "/war", produces = "application/json;charset=utf-8")
    public void warnWar(@RequestBody WarnDto warData) {
        log.info("new war data request");
        log.info("data = {}", warData.getWarnInfo());
        sseEmitters.warnWar(warData);
    }


    @GetMapping("/test")
    public long test() {
        return sseEmitters.test();
    }


}