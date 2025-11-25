<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="solicitud">
        <h2 class="jh-entity-heading" data-cy="solicitudDetailsHeading">
          <span v-text="t$('eApoyoApp.solicitud.detail.title')"></span> {{ solicitud.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.curp')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.curp }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.nombre')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.nombre }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.primerApellido')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.primerApellido }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.segundoApellido')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.segundoApellido }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.genero')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.genero }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.desc')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.desc }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.keywords')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.keywords }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.ineUrl')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.ineUrl }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.cvUrl')"></span>
          </dt>
          <dd>
            <span>{{ solicitud.cvUrl }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.estado')"></span>
          </dt>
          <dd>
            <span v-text="t$('eApoyoApp.Estado.' + solicitud.estado)"></span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.solicitud.apoyo')"></span>
          </dt>
          <dd>
            <div v-if="solicitud.apoyo">
              <router-link :to="{ name: 'ApoyoView', params: { apoyoId: solicitud.apoyo.id } }">{{ solicitud.apoyo.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="solicitud.id"
          :to="{ name: 'SolicitudEdit', params: { solicitudId: solicitud.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./solicitud-details.component.ts"></script>
