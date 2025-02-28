import { FormControl } from "@angular/forms";
import { Users } from "./users.interface";
import { Teams } from "./teams.interface";

export interface MembersForm {
    id      : FormControl<string|null>,
    id_user : FormControl,
    id_team : FormControl,
    active  : FormControl
}

export interface Members {
    id         : bigint,
    id_user    : bigint,
    id_team    : bigint,
    active     : boolean
    id_account : bigint
}

export interface MembersResponse {
    id         : bigint,
    active     : boolean,
    user       : Users,
    teams      : Teams
}

