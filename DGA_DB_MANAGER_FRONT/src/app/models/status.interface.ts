import { FormControl } from "@angular/forms";

export interface Status {
    id         : string,
    name       : string,
    timer      : boolean
}

export interface StatusForm {
    id         : FormControl<string|null>,
    name       : FormControl,
    timer      : FormControl
  }

