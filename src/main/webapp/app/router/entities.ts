import { Authority } from '@/shared/security/authority';
const Entities = () => import('@/entities/entities.vue');

const Solicitud = () => import('@/entities/solicitud/solicitud.vue');
const SolicitudUpdate = () => import('@/entities/solicitud/solicitud-update.vue');
const SolicitudDetails = () => import('@/entities/solicitud/solicitud-details.vue');

const Apoyo = () => import('@/entities/apoyo/apoyo.vue');
const ApoyoUpdate = () => import('@/entities/apoyo/apoyo-update.vue');
const ApoyoDetails = () => import('@/entities/apoyo/apoyo-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'solicitud',
      name: 'Solicitud',
      component: Solicitud,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/new',
      name: 'SolicitudCreate',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/:solicitudId/edit',
      name: 'SolicitudEdit',
      component: SolicitudUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'solicitud/:solicitudId/view',
      name: 'SolicitudView',
      component: SolicitudDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'apoyo',
      name: 'Apoyo',
      component: Apoyo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'apoyo/new',
      name: 'ApoyoCreate',
      component: ApoyoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'apoyo/:apoyoId/edit',
      name: 'ApoyoEdit',
      component: ApoyoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'apoyo/:apoyoId/view',
      name: 'ApoyoView',
      component: ApoyoDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
