package mx.infotec.eapoyo.domain;

import java.util.UUID;

public class SolicitudTestSamples {

    public static Solicitud getSolicitudSample1() {
        return new Solicitud()
            .id("id1")
            .curp("curp1")
            .nombre("nombre1")
            .primerApellido("primerApellido1")
            .segundoApellido("segundoApellido1")
            .genero("genero1")
            .desc("desc1")
            .keywords("keywords1")
            .ineUrl("ineUrl1")
            .cvUrl("cvUrl1");
    }

    public static Solicitud getSolicitudSample2() {
        return new Solicitud()
            .id("id2")
            .curp("curp2")
            .nombre("nombre2")
            .primerApellido("primerApellido2")
            .segundoApellido("segundoApellido2")
            .genero("genero2")
            .desc("desc2")
            .keywords("keywords2")
            .ineUrl("ineUrl2")
            .cvUrl("cvUrl2");
    }

    public static Solicitud getSolicitudRandomSampleGenerator() {
        return new Solicitud()
            .id(UUID.randomUUID().toString())
            .curp(UUID.randomUUID().toString())
            .nombre(UUID.randomUUID().toString())
            .primerApellido(UUID.randomUUID().toString())
            .segundoApellido(UUID.randomUUID().toString())
            .genero(UUID.randomUUID().toString())
            .desc(UUID.randomUUID().toString())
            .keywords(UUID.randomUUID().toString())
            .ineUrl(UUID.randomUUID().toString())
            .cvUrl(UUID.randomUUID().toString());
    }
}
