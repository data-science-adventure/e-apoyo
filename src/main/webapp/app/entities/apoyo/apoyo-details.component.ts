import { type Ref, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ApoyoService from './apoyo.service';
import { type IApoyo } from '@/shared/model/apoyo.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApoyoDetails',
  setup() {
    const apoyoService = inject('apoyoService', () => new ApoyoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const apoyo: Ref<IApoyo> = ref({});

    const retrieveApoyo = async apoyoId => {
      try {
        const res = await apoyoService().find(apoyoId);
        apoyo.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.apoyoId) {
      retrieveApoyo(route.params.apoyoId);
    }

    return {
      alertService,
      apoyo,

      previousState,
      t$: useI18n().t,
    };
  },
});
