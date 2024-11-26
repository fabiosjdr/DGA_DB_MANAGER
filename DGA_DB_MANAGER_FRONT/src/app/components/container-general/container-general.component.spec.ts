import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContainerGeneralComponent } from './container-general.component';

describe('ContainerGeneralComponent', () => {
  let component: ContainerGeneralComponent;
  let fixture: ComponentFixture<ContainerGeneralComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContainerGeneralComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContainerGeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
