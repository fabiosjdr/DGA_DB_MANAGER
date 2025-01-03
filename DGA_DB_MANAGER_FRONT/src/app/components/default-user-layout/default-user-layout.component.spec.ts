import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultUserLayoutComponent } from './default-user-layout.component';

describe('DefaultUserLayoutComponent', () => {
  let component: DefaultUserLayoutComponent;
  let fixture: ComponentFixture<DefaultUserLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DefaultUserLayoutComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DefaultUserLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
