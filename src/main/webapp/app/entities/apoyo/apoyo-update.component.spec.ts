import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApoyoUpdate from './apoyo-update.vue';
import ApoyoService from './apoyo.service';
import AlertService from '@/shared/alert/alert.service';

type ApoyoUpdateComponentType = InstanceType<typeof ApoyoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const apoyoSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ApoyoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Apoyo Management Update Component', () => {
    let comp: ApoyoUpdateComponentType;
    let apoyoServiceStub: SinonStubbedInstance<ApoyoService>;

    beforeEach(() => {
      route = {};
      apoyoServiceStub = sinon.createStubInstance<ApoyoService>(ApoyoService);
      apoyoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          apoyoService: () => apoyoServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ApoyoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apoyo = apoyoSample;
        apoyoServiceStub.update.resolves(apoyoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apoyoServiceStub.update.calledWith(apoyoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        apoyoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ApoyoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.apoyo = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apoyoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        apoyoServiceStub.find.resolves(apoyoSample);
        apoyoServiceStub.retrieve.resolves([apoyoSample]);

        // WHEN
        route = {
          params: {
            apoyoId: `${apoyoSample.id}`,
          },
        };
        const wrapper = shallowMount(ApoyoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.apoyo).toMatchObject(apoyoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        apoyoServiceStub.find.resolves(apoyoSample);
        const wrapper = shallowMount(ApoyoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
