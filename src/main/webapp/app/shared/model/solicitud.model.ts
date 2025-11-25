import { type IApoyo } from '@/shared/model/apoyo.model';

import { type Estado } from '@/shared/model/enumerations/estado.model';
export interface ISolicitud {
  id?: string;
  curp?: string | null;
  nombre?: string | null;
  primerApellido?: string | null;
  segundoApellido?: string | null;
  genero?: string | null;
  desc?: string | null;
  keywords?: string | null;
  ineUrl?: string | null;
  cvUrl?: string | null;
  estado?: keyof typeof Estado | null;
  apoyo?: IApoyo | null;
}

export class Solicitud implements ISolicitud {
  constructor(
    public id?: string,
    public curp?: string | null,
    public nombre?: string | null,
    public primerApellido?: string | null,
    public segundoApellido?: string | null,
    public genero?: string | null,
    public desc?: string | null,
    public keywords?: string | null,
    public ineUrl?: string | null,
    public cvUrl?: string | null,
    public estado?: keyof typeof Estado | null,
    public apoyo?: IApoyo | null,
  ) {}
}
