<template>
  <div>
    <h2 id="page-heading" data-cy="SolicitudHeading">
      <span v-text="t$('eApoyoApp.solicitud.home.title')" id="solicitud-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('eApoyoApp.solicitud.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'SolicitudCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-solicitud"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('eApoyoApp.solicitud.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && solicituds && solicituds.length === 0">
      <span v-text="t$('eApoyoApp.solicitud.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="solicituds && solicituds.length > 0">
      <table class="table table-striped" aria-describedby="solicituds">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('curp')">
              <span v-text="t$('eApoyoApp.solicitud.curp')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'curp'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nombre')">
              <span v-text="t$('eApoyoApp.solicitud.nombre')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nombre'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('primerApellido')">
              <span v-text="t$('eApoyoApp.solicitud.primerApellido')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'primerApellido'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('segundoApellido')">
              <span v-text="t$('eApoyoApp.solicitud.segundoApellido')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'segundoApellido'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('genero')">
              <span v-text="t$('eApoyoApp.solicitud.genero')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'genero'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('desc')">
              <span v-text="t$('eApoyoApp.solicitud.desc')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'desc'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('keywords')">
              <span v-text="t$('eApoyoApp.solicitud.keywords')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'keywords'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('ineUrl')">
              <span v-text="t$('eApoyoApp.solicitud.ineUrl')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ineUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('cvUrl')">
              <span v-text="t$('eApoyoApp.solicitud.cvUrl')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cvUrl'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('estado')">
              <span v-text="t$('eApoyoApp.solicitud.estado')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estado'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('apoyo.id')">
              <span v-text="t$('eApoyoApp.solicitud.apoyo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'apoyo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="solicitud in solicituds" :key="solicitud.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SolicitudView', params: { solicitudId: solicitud.id } }">{{ solicitud.id }}</router-link>
            </td>
            <td>{{ solicitud.curp }}</td>
            <td>{{ solicitud.nombre }}</td>
            <td>{{ solicitud.primerApellido }}</td>
            <td>{{ solicitud.segundoApellido }}</td>
            <td>{{ solicitud.genero }}</td>
            <td>{{ solicitud.desc }}</td>
            <td>{{ solicitud.keywords }}</td>
            <td>{{ solicitud.ineUrl }}</td>
            <td>{{ solicitud.cvUrl }}</td>
            <td v-text="t$('eApoyoApp.Estado.' + solicitud.estado)"></td>
            <td>
              <div v-if="solicitud.apoyo">
                <router-link :to="{ name: 'ApoyoView', params: { apoyoId: solicitud.apoyo.id } }">{{ solicitud.apoyo.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SolicitudView', params: { solicitudId: solicitud.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SolicitudEdit', params: { solicitudId: solicitud.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  @click="prepareRemove(solicitud)"
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
        <span id="eApoyoApp.solicitud.delete.question" data-cy="solicitudDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-solicitud-heading" v-text="t$('eApoyoApp.solicitud.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" @click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-solicitud"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            @click="removeSolicitud()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="solicituds && solicituds.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./solicitud.component.ts"></script>
