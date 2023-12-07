package com.taba4.salida.warning;

import com.taba4.salida.warning.dto.EqkDto;
import com.taba4.salida.warning.dto.WarnDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SseEmitters {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter connect(SseEmitter emitter) throws IOException {
        this.emitters.add(emitter);
        log.info("new emitter connected: {}", emitter);
        log.info("emitters size: {}", emitters.size());
        log.info("emitter list: {}", emitters);

        WarnDto dummy = new WarnDto("connected");
        emitter.send(SseEmitter.event()
                .name("connect")
                .data(dummy, MediaType.APPLICATION_JSON));

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            log.info("emitter list: {}", emitters);
            this.emitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            log.info("emitter list: {}", emitters);
            this.emitters.remove(emitter);
        });
        return emitter;
    }

    public void warnEqk(EqkDto eqkData) {     //위도 경도를 받아 emitters에 뿌리기
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("earthquake")
                        .data(eqkData, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });
    }

    public void warnWar(WarnDto warData) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("war")
                        .data(warData, MediaType.APPLICATION_JSON));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });
    }


    public long test() {
        long dummydata = 3;
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("test")
                        .data(dummydata));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        });
        return dummydata;
    }


}
