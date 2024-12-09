import { Stage } from "./stage.interface";

export interface Detail {
    id         : bigint,
    id_activity: bigint,
    title      : string,
    description: string,
    id_user    : bigint,
    stage      : Stage
}