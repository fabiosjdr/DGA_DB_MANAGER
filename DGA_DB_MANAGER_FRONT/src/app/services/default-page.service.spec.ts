import { TestBed } from '@angular/core/testing';

import { DefaultPageService } from './default-page.service';

describe('DefaultPageService', () => {
  let service: DefaultPageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DefaultPageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
