<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2
          id="eApoyoApp.apoyo.home.createOrEditLabel"
          data-cy="ApoyoCreateUpdateHeading"
          v-text="t$('eApoyoApp.apoyo.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="apoyo.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="apoyo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.apoyo.nombre')" for="apoyo-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="apoyo-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.apoyo.desc')" for="apoyo-desc"></label>
            <input
              type="text"
              class="form-control"
              name="desc"
              id="apoyo-desc"
              data-cy="desc"
              :class="{ valid: !v$.desc.$invalid, invalid: v$.desc.$invalid }"
              v-model="v$.desc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.apoyo.prerrequisitos')" for="apoyo-prerrequisitos"></label>
            <input
              type="text"
              class="form-control"
              name="prerrequisitos"
              id="apoyo-prerrequisitos"
              data-cy="prerrequisitos"
              :class="{ valid: !v$.prerrequisitos.$invalid, invalid: v$.prerrequisitos.$invalid }"
              v-model="v$.prerrequisitos.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.apoyo.keywords')" for="apoyo-keywords"></label>
            <input
              type="text"
              class="form-control"
              name="keywords"
              id="apoyo-keywords"
              data-cy="keywords"
              :class="{ valid: !v$.keywords.$invalid, invalid: v$.keywords.$invalid }"
              v-model="v$.keywords.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('eApoyoApp.apoyo.tipo')" for="apoyo-tipo"></label>
            <select
              class="form-control"
              name="tipo"
              :class="{ valid: !v$.tipo.$invalid, invalid: v$.tipo.$invalid }"
              v-model="v$.tipo.$model"
              id="apoyo-tipo"
              data-cy="tipo"
            >
              <option
                v-for="tipoApoyo in tipoApoyoValues"
                :key="tipoApoyo"
                :value="tipoApoyo"
                :label="t$('eApoyoApp.TipoApoyo.' + tipoApoyo)"
              >
                {{ tipoApoyo }}
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
<script lang="ts" src="./apoyo-update.component.ts"></script>
