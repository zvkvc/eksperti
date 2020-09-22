import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSubforumsComponent } from './list-subforums.component';

describe('ListSubforumsComponent', () => {
  let component: ListSubforumsComponent;
  let fixture: ComponentFixture<ListSubforumsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListSubforumsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListSubforumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
