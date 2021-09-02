import { TestBed } from '@angular/core/testing';

import { SharedfavoriteslistService } from './sharedfavoriteslist.service';

describe('SharedfavoriteslistService', () => {
  let service: SharedfavoriteslistService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedfavoriteslistService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
