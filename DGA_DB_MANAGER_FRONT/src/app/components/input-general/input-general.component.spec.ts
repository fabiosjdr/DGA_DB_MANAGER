import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputGeneralComponent } from './input-general.component';

describe('InputGeneralComponent', () => {
  let component: InputGeneralComponent;
  let fixture: ComponentFixture<InputGeneralComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputGeneralComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
