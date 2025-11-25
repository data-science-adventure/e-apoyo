import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SolicitudService from './solicitud.service';
import { type ISolicitud } from '@/shared/model/solicitud.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SolicitudDetails',
  setup() {
    const solicitudService = inject('solicitudService', () => new SolicitudService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const solicitud: Ref<ISolicitud> = ref({});

    const retrieveSolicitud = async solicitudId => {
      try {
        const res = await solicitudService().find(solicitudId);
        solicitud.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.solicitudId) {
      retrieveSolicitud(route.params.solicitudId);
    }

    return {
      alertService,
      solicitud,

      previousState,
      t$: useI18n().t,
    };
  },
});
