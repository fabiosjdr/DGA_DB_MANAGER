import { FormControl } from "@angular/forms";
import { UserRoles } from "./user_roles.interface";

export interface Users {
    id         : bigint,
    name       : string,
    email      : string,
    password   : string,
    active     : boolean,
    role       : UserRoles
}

export interface UsersForm {

    id                 : FormControl<BigInt|null>,
    name               : FormControl<string|null>,
    email              : FormControl<string|null>,
    password           : FormControl<string|null>,
    confirm_password   : FormControl<string|null>,
    active             : FormControl<boolean|null>,
    id_role            : FormControl
}