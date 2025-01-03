import { FormControl } from "@angular/forms";

export interface ActivityForm {
    id         : FormControl<string|null>,
    activity   : FormControl,
    id_category: FormControl,
    id_client  : FormControl,
    id_project : FormControl,
    start_date : FormControl,
    end_date   : FormControl,
    id_account : FormControl
}

export interface Activity {
    id         : bigint,
    activity   : string,
    id_category: bigint,
    id_client  : bigint,
    id_project : bigint,
    id_user    : bigint,
    start_date : string,
    end_date   : string,
    id_account : bigint
}

