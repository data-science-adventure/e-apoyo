import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SolicitudDetails from './solicitud-details.vue';
import SolicitudService from './solicitud.service';
import AlertService from '@/shared/alert/alert.service';

type SolicitudDetailsComponentType = InstanceType<typeof SolicitudDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const solicitudSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Solicitud Management Detail Component', () => {
    let solicitudServiceStub: SinonStubbedInstance<SolicitudService>;
    let mountOptions: MountingOptions<SolicitudDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      solicitudServiceStub = sinon.createStubInstance<SolicitudService>(SolicitudService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          solicitudService: () => solicitudServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        solicitudServiceStub.find.resolves(solicitudSample);
        route = {
          params: {
            solicitudId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(SolicitudDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.solicitud).toMatchObject(solicitudSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        solicitudServiceStub.find.resolves(solicitudSample);
        const wrapper = shallowMount(SolicitudDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
