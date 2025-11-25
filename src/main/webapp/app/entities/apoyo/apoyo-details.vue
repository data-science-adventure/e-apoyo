<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apoyo">
        <h2 class="jh-entity-heading" data-cy="apoyoDetailsHeading">
          <span v-text="t$('eApoyoApp.apoyo.detail.title')"></span> {{ apoyo.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('eApoyoApp.apoyo.nombre')"></span>
          </dt>
          <dd>
            <span>{{ apoyo.nombre }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.apoyo.desc')"></span>
          </dt>
          <dd>
            <span>{{ apoyo.desc }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.apoyo.prerrequisitos')"></span>
          </dt>
          <dd>
            <span>{{ apoyo.prerrequisitos }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.apoyo.keywords')"></span>
          </dt>
          <dd>
            <span>{{ apoyo.keywords }}</span>
          </dd>
          <dt>
            <span v-text="t$('eApoyoApp.apoyo.tipo')"></span>
          </dt>
          <dd>
            <span v-text="t$('eApoyoApp.TipoApoyo.' + apoyo.tipo)"></span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="apoyo.id" :to="{ name: 'ApoyoEdit', params: { apoyoId: apoyo.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./apoyo-details.component.ts"></script>
