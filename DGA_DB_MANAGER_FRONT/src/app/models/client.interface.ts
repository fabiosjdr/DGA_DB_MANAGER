import { FormControl } from "@angular/forms";

export interface Client {

    id         : bigint,
    name       : string,
    responsable: string,
    telephone  : string,
    hours      : string
}


export interface ClientForm {
    id         : FormControl<string|null>,
    name       : FormControl,
    responsable: FormControl,
    telephone  : FormControl,
    hours      : FormControl
}