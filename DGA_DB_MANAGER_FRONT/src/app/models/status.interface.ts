import { FormControl } from "@angular/forms";

export interface Status {
    id         : string,
    name       : string
}

export interface StatusForm {
    id         : FormControl<string|null>,
    name       : FormControl
  }

