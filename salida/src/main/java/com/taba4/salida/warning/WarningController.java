package com.taba4.salida.warning;

import com.taba4.salida.warning.dto.EqkDto;
import com.taba4.salida.warning.dto.WarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/warn")
class WarningController {

    private final SseEmitters sseEmitters;

    WarningController(SseEmitters sseEmitters) {

        this.sseEmitters = sseEmitters;
    }

    @GetMapping(value = "/connect", produces = "text/event-stream")
    public SseEmitter connect() {                   //++ ResponseEntity 사용하기
        SseEmitter emitter = new SseEmitter();      //++ timeout 설정하기
        return sseEmitters.connect(emitter);
    }

    @PostMapping(path = "/eqk")
    public void warnEqk(@RequestBody EqkDto eqkData) {
        log.info("new eqk data request");
        log.info("data = {}, {}, {}", eqkData.getLatitude(), eqkData.getLongitude(), eqkData.getMagnitude());
        sseEmitters.warnEqk(eqkData);
    }

    @PostMapping(path = "/war", produces = "application/json;charset=utf-8")
    public void warnWar(@RequestBody WarDto warData) {
        log.info("new war data request");
        log.info("data = {}", warData.getWarInfo());
        sseEmitters.warnWar(warData);
    }


    @GetMapping("/test")
    public long test() {
        return sseEmitters.test();
    }


}