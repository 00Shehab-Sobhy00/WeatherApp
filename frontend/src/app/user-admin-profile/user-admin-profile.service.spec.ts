import { TestBed } from '@angular/core/testing';

import { EditeUserAdminProfile } from './EditeProfile.service';

describe('UserAdminProfileService', () => {
  let service: EditeUserAdminProfile;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditeUserAdminProfile);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
