package mx.infotec.eapoyo.domain;

import java.util.UUID;

public class ApoyoTestSamples {

    public static Apoyo getApoyoSample1() {
        return new Apoyo().id("id1").nombre("nombre1").desc("desc1").prerrequisitos("prerrequisitos1").keywords("keywords1");
    }

    public static Apoyo getApoyoSample2() {
        return new Apoyo().id("id2").nombre("nombre2").desc("desc2").prerrequisitos("prerrequisitos2").keywords("keywords2");
    }

    public static Apoyo getApoyoRandomSampleGenerator() {
        return new Apoyo()
            .id(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .desc(UUID.randomUUID().toString())
            .prerrequisitos(UUID.randomUUID().toString())
            .keywords(UUID.randomUUID().toString());
    }
}
