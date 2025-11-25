<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="eApoyoApp.solicitud.home.createOrEditLabel"
          data-cy="SolicitudCreateUpdateHeading"
          v-text="t$('eApoyoApp.solicitud.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="solicitud.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="solicitud.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.curp')" for="solicitud-curp"></label>
            <input
              type="text"
              class="form-control"
              name="curp"
              id="solicitud-curp"
              data-cy="curp"
              :class="{ valid: !v$.curp.$invalid, invalid: v$.curp.$invalid }"
              v-model="v$.curp.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.nombre')" for="solicitud-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="solicitud-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.primerApellido')" for="solicitud-primerApellido"></label>
            <input
              type="text"
              class="form-control"
              name="primerApellido"
              id="solicitud-primerApellido"
              data-cy="primerApellido"
              :class="{ valid: !v$.primerApellido.$invalid, invalid: v$.primerApellido.$invalid }"
              v-model="v$.primerApellido.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.segundoApellido')" for="solicitud-segundoApellido"></label>
            <input
              type="text"
              class="form-control"
              name="segundoApellido"
              id="solicitud-segundoApellido"
              data-cy="segundoApellido"
              :class="{ valid: !v$.segundoApellido.$invalid, invalid: v$.segundoApellido.$invalid }"
              v-model="v$.segundoApellido.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.genero')" for="solicitud-genero"></label>
            <input
              type="text"
              class="form-control"
              name="genero"
              id="solicitud-genero"
              data-cy="genero"
              :class="{ valid: !v$.genero.$invalid, invalid: v$.genero.$invalid }"
              v-model="v$.genero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.desc')" for="solicitud-desc"></label>
            <input
              type="text"
              class="form-control"
              name="desc"
              id="solicitud-desc"
              data-cy="desc"
              :class="{ valid: !v$.desc.$invalid, invalid: v$.desc.$invalid }"
              v-model="v$.desc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.keywords')" for="solicitud-keywords"></label>
            <input
              type="text"
              class="form-control"
              name="keywords"
              id="solicitud-keywords"
              data-cy="keywords"
              :class="{ valid: !v$.keywords.$invalid, invalid: v$.keywords.$invalid }"
              v-model="v$.keywords.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.ineUrl')" for="solicitud-ineUrl"></label>
            <input
              type="text"
              class="form-control"
              name="ineUrl"
              id="solicitud-ineUrl"
              data-cy="ineUrl"
              :class="{ valid: !v$.ineUrl.$invalid, invalid: v$.ineUrl.$invalid }"
              v-model="v$.ineUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.cvUrl')" for="solicitud-cvUrl"></label>
            <input
              type="text"
              class="form-control"
              name="cvUrl"
              id="solicitud-cvUrl"
              data-cy="cvUrl"
              :class="{ valid: !v$.cvUrl.$invalid, invalid: v$.cvUrl.$invalid }"
              v-model="v$.cvUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.estado')" for="solicitud-estado"></label>
            <select
              class="form-control"
              name="estado"
              :class="{ valid: !v$.estado.$invalid, invalid: v$.estado.$invalid }"
              v-model="v$.estado.$model"
              id="solicitud-estado"
              data-cy="estado"
            >
              <option v-for="estado in estadoValues" :key="estado" :value="estado" :label="t$('eApoyoApp.Estado.' + estado)">
                {{ estado }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.solicitud.apoyo')" for="solicitud-apoyo"></label>
            <select class="form-control" id="solicitud-apoyo" data-cy="apoyo" name="apoyo" v-model="solicitud.apoyo">
              <option :value="null"></option>
              <option
                :value="solicitud.apoyo && apoyoOption.id === solicitud.apoyo.id ? solicitud.apoyo : apoyoOption"
                v-for="apoyoOption in apoyos"
                :key="apoyoOption.id"
              >
                {{ apoyoOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts"></script>
