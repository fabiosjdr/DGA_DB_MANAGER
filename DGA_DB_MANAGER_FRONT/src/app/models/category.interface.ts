import { FormControl } from "@angular/forms";

export interface Category {
    id         : string,
    name       : string
}

export interface CategoryForm {
    id         : FormControl<string|null>,
    name       : FormControl
  }

