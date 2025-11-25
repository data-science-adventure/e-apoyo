<template>
  <div>
    <h2 id="page-heading" data-cy="ApoyoHeading">
      <span v-text="t$('eApoyoApp.apoyo.home.title')" id="apoyo-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('eApoyoApp.apoyo.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ApoyoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-apoyo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('eApoyoApp.apoyo.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apoyos && apoyos.length === 0">
      <span v-text="t$('eApoyoApp.apoyo.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="apoyos && apoyos.length > 0">
      <table class="table table-striped" aria-describedby="apoyos">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nombre')">
              <span v-text="t$('eApoyoApp.apoyo.nombre')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nombre'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('desc')">
              <span v-text="t$('eApoyoApp.apoyo.desc')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'desc'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('prerrequisitos')">
              <span v-text="t$('eApoyoApp.apoyo.prerrequisitos')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prerrequisitos'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('keywords')">
              <span v-text="t$('eApoyoApp.apoyo.keywords')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('tipo')">
              <span v-text="t$('eApoyoApp.apoyo.tipo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipo'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apoyo in apoyos" :key="apoyo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApoyoView', params: { apoyoId: apoyo.id } }">{{ apoyo.id }}</router-link>
            </td>
            <td>{{ apoyo.nombre }}</td>
            <td>{{ apoyo.desc }}</td>
            <td>{{ apoyo.prerrequisitos }}</td>
            <td>{{ apoyo.keywords }}</td>
            <td v-text="t$('eApoyoApp.TipoApoyo.' + apoyo.tipo)"></td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApoyoView', params: { apoyoId: apoyo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApoyoEdit', params: { apoyoId: apoyo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(apoyo)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="eApoyoApp.apoyo.delete.question" data-cy="apoyoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-apoyo-heading" v-text="t$('eApoyoApp.apoyo.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-apoyo"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeApoyo()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="apoyos && apoyos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./apoyo.component.ts"></script>
