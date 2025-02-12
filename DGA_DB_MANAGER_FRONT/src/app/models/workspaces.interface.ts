import { FormControl } from "@angular/forms";

export interface Workspaces {
    id         : string,
    name       : string
}

export interface WorkspacesForm {
    id         : FormControl<string|null>,
    name       : FormControl
  }

