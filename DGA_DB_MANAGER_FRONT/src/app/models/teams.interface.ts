import { FormControl } from "@angular/forms";

export interface TeamsForm {
    id            : FormControl<bigint|null>,
    id_workspaces : FormControl<bigint|null>,
    title         : FormControl,
    description   : FormControl
}

export interface Teams {
    id            : bigint,
    id_workspaces : bigint,
    title         : string,
    description   : string,
    id_account    : bigint
}

