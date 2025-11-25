import { defineComponent, provide } from 'vue';

import SolicitudService from './solicitud/solicitud.service';
import ApoyoService from './apoyo/apoyo.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('solicitudService', () => new SolicitudService());
    provide('apoyoService', () => new ApoyoService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
