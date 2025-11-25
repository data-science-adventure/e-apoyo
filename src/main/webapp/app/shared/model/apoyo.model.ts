import { type TipoApoyo } from '@/shared/model/enumerations/tipo-apoyo.model';
export interface IApoyo {
  id?: string;
  nombre?: string | null;
  desc?: string | null;
  prerrequisitos?: string | null;
  keywords?: string | null;
  tipo?: keyof typeof TipoApoyo | null;
}

export class Apoyo implements IApoyo {
  constructor(
    public id?: string,
    public nombre?: string | null,
    public desc?: string | null,
    public prerrequisitos?: string | null,
    public keywords?: string | null,
    public tipo?: keyof typeof TipoApoyo | null,
  ) {}
}
