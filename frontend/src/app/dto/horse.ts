import { Owner } from "./owner";

export interface Horse {
  id?: number;
  name: string;
  description: string;
  birthdate: Date;
  sex: string;
  owner?: Owner;
  horseFather?: Horse;
  horseMother?: Horse;
}
