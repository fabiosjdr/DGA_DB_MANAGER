import { TestBed } from '@angular/core/testing';

import { ReportActivityService } from './report-activity.service';

describe('ReportActivityService', () => {
  let service: ReportActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
