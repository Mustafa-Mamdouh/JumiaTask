import { NumberData } from "./Number";

export interface NumberResponse {
  total?: number;
  pageSize?:number;
  pageNumber?: number;
  numberOfElements?: number;
  data?: NumberData[];
}
