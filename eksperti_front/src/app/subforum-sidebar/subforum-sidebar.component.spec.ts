import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubforumSideBarComponent } from './subforum-sidebar.component';

describe('SubforumSideBarComponent', () => {
  let component: SubforumSideBarComponent;
  let fixture: ComponentFixture<SubforumSideBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubforumSideBarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubforumSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
