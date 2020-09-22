import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubforumComponent } from './create-subforum.component';

describe('CreateSubforumComponent', () => {
  let component: CreateSubforumComponent;
  let fixture: ComponentFixture<CreateSubforumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSubforumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubforumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
