import { vitest } from 'vitest';
import { type MountingOptions, shallowMount } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SolicitudUpdate from './solicitud-update.vue';
import SolicitudService from './solicitud.service';
import AlertService from '@/shared/alert/alert.service';

import ApoyoService from '@/entities/apoyo/apoyo.service';

type SolicitudUpdateComponentType = InstanceType<typeof SolicitudUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const solicitudSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<SolicitudUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Solicitud Management Update Component', () => {
    let comp: SolicitudUpdateComponentType;
    let solicitudServiceStub: SinonStubbedInstance<SolicitudService>;

    beforeEach(() => {
      route = {};
      solicitudServiceStub = sinon.createStubInstance<SolicitudService>(SolicitudService);
      solicitudServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          solicitudService: () => solicitudServiceStub,
          apoyoService: () =>
            sinon.createStubInstance<ApoyoService>(ApoyoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(SolicitudUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.solicitud = solicitudSample;
        solicitudServiceStub.update.resolves(solicitudSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solicitudServiceStub.update.calledWith(solicitudSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        solicitudServiceStub.create.resolves(entity);
        const wrapper = shallowMount(SolicitudUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.solicitud = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solicitudServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        solicitudServiceStub.find.resolves(solicitudSample);
        solicitudServiceStub.retrieve.resolves([solicitudSample]);

        // WHEN
        route = {
          params: {
            solicitudId: `${solicitudSample.id}`,
          },
        };
        const wrapper = shallowMount(SolicitudUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.solicitud).toMatchObject(solicitudSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        solicitudServiceStub.find.resolves(solicitudSample);
        const wrapper = shallowMount(SolicitudUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
