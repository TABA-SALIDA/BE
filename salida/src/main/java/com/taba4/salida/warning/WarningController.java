package com.taba4.salida.warning;

import com.taba4.salida.warning.dto.EqkDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


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

    @PostMapping("/eqk")
    public void warnEqk(@RequestBody EqkDto eqkData) {

        sseEmitters.warnEqk(eqkData);
    }

    @PostMapping("/war")
    public void warnWar(@RequestBody EqkDto warData) {

        sseEmitters.warnEqk(warData);
    }

    /*
    @GetMapping("/test")
    public long test() {
        return emitterRepository.test();
    }
     */

}