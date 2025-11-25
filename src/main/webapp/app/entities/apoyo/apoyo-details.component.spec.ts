import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApoyoDetails from './apoyo-details.vue';
import ApoyoService from './apoyo.service';
import AlertService from '@/shared/alert/alert.service';

type ApoyoDetailsComponentType = InstanceType<typeof ApoyoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apoyoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Apoyo Management Detail Component', () => {
    let apoyoServiceStub: SinonStubbedInstance<ApoyoService>;
    let mountOptions: MountingOptions<ApoyoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      apoyoServiceStub = sinon.createStubInstance<ApoyoService>(ApoyoService);

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
          apoyoService: () => apoyoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        apoyoServiceStub.find.resolves(apoyoSample);
        route = {
          params: {
            apoyoId: '' + 'ABC',
          },
        };
        const wrapper = shallowMount(ApoyoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.apoyo).toMatchObject(apoyoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apoyoServiceStub.find.resolves(apoyoSample);
        const wrapper = shallowMount(ApoyoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
