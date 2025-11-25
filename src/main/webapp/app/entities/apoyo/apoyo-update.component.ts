import { type Ref, computed, defineComponent, inject, ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ApoyoService from './apoyo.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { Apoyo, type IApoyo } from '@/shared/model/apoyo.model';
import { TipoApoyo } from '@/shared/model/enumerations/tipo-apoyo.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApoyoUpdate',
  setup() {
    const apoyoService = inject('apoyoService', () => new ApoyoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const apoyo: Ref<IApoyo> = ref(new Apoyo());
    const tipoApoyoValues: Ref<string[]> = ref(Object.keys(TipoApoyo));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nombre: {},
      desc: {},
      prerrequisitos: {},
      keywords: {},
      tipo: {},
    };
    const v$ = useVuelidate(validationRules, apoyo as any);
    v$.value.$validate();

    return {
      apoyoService,
      alertService,
      apoyo,
      previousState,
      tipoApoyoValues,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.apoyo.id) {
        this.apoyoService()
          .update(this.apoyo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('eApoyoApp.apoyo.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.apoyoService()
          .create(this.apoyo)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('eApoyoApp.apoyo.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
