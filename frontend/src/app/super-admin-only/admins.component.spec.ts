import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminsComponent } from './admins.component';

describe('SuperAdminComponent', () => {
  let component: AdminsComponent;
  let fixture: ComponentFixture<AdminsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
