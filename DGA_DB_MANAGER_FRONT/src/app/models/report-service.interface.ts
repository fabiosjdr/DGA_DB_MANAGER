//import { Stage } from "./stage.interface";
//import { Users } from "./users.interface";

export interface ReportActivity {
    id_activity_detail: bigint,
    id_activity       : bigint,
    id_stage          : bigint,
    title             : string,
    stage_name        : string,
    id_user           : bigint,
    user_name         : string,
    duration          : number
}