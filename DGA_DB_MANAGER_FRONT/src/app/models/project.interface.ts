import { FormControl } from "@angular/forms";

export interface Project {

    id         : bigint,
    name       : string,
    description: string,
    id_client  : string,
    start_date : string,
    end_date   : string
}


export interface ProjectForm {

    id         : FormControl<BigInt|null>,
    name       : FormControl<string|null>,
    description: FormControl<string|null>,
    id_client  : FormControl,
    start_date : FormControl,
    end_date   : FormControl
}