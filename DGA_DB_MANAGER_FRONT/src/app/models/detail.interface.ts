import { Stage } from "./stage.interface";
import { Users } from "./users.interface";

export interface Detail {
    id         : bigint,
    id_activity: bigint,
    title      : string,
    description: string,
    priority   : string,
    color      : string,
    progress   : number,
    start_date : Date,
    due_date   : Date,
    user       : Users,
    stage      : Stage
}