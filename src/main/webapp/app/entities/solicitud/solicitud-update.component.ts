import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SolicitudService from './solicitud.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ApoyoService from '@/entities/apoyo/apoyo.service';
import { type IApoyo } from '@/shared/model/apoyo.model';
import { type ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import { Estado } from '@/shared/model/enumerations/estado.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SolicitudUpdate',
  setup() {
    const solicitudService = inject('solicitudService', () => new SolicitudService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const solicitud: Ref<ISolicitud> = ref(new Solicitud());

    const apoyoService = inject('apoyoService', () => new ApoyoService());

    const apoyos: Ref<IApoyo[]> = ref([]);
    const estadoValues: Ref<string[]> = ref(Object.keys(Estado));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      apoyoService()
        .retrieve()
        .then(res => {
          apoyos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      curp: {},
      nombre: {},
      primerApellido: {},
      segundoApellido: {},
      genero: {},
      desc: {},
      keywords: {},
      ineUrl: {},
      cvUrl: {},
      estado: {},
      apoyo: {},
    };
    const v$ = useVuelidate(validationRules, solicitud as any);
    v$.value.$validate();

    return {
      solicitudService,
      alertService,
      solicitud,
      previousState,
      estadoValues,
      isSaving,
      currentLanguage,
      apoyos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.solicitud.id) {
        this.solicitudService()
          .update(this.solicitud)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('eApoyoApp.solicitud.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.solicitudService()
          .create(this.solicitud)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('eApoyoApp.solicitud.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
