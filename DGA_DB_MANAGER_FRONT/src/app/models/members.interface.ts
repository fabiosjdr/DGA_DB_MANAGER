import { FormControl } from "@angular/forms";

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

