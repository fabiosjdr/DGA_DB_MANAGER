import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultAutocompleteMaterialComponent } from './default-autocomplete-material.component';

describe('DefaultAutocompleteMaterialComponent', () => {
  let component: DefaultAutocompleteMaterialComponent;
  let fixture: ComponentFixture<DefaultAutocompleteMaterialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DefaultAutocompleteMaterialComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultAutocompleteMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
