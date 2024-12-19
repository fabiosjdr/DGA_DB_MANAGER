import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanColumnCustomHeaderComponent } from './kanban-column-custom-header.component';

describe('KanbanColumnCustomHeaderComponent', () => {
  let component: KanbanColumnCustomHeaderComponent;
  let fixture: ComponentFixture<KanbanColumnCustomHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KanbanColumnCustomHeaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(KanbanColumnCustomHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
