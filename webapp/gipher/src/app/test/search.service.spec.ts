import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { inject, TestBed } from '@angular/core/testing';
import { Gif } from '../models/Gif';

import { SearchService } from '../services/search.service';

describe('SearchService', () => {
  const gifs: Gif[] = [
    {
        userId: 1, 
        gifURL: "test1",
        favorite: false
    },
    {
      userId: 2, 
      gifURL: "test2",
      favorite: false
    },
    {
      userId: 3, 
      gifURL: "test3",
      favorite: false
    },
  ]
  
  let service: SearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[SearchService]
    });
    service = TestBed.inject(SearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // testing service for getting all gifs method
  it('searchGifs() should fetch all gifs',
    inject([HttpTestingController, SearchService],
      (httpMock: HttpTestingController, service: SearchService) => {
        // We call the service
        service.searchGifs("searchTermTest").subscribe(data => {
          console.log(data);
        });
        // We set the expectations for the HttpClient mock
        const req = httpMock.expectOne('http://localhost:9090/user-service/searchTerm/searchTermTest');
        expect(req.request.method).toEqual('GET');
        // Then we set the fake data to be returned by the mock
        req.flush({data:gifs });
        // httpMock.verify();
      })
  );

  afterEach(inject([HttpTestingController], (httpMock: HttpTestingController) => {
    httpMock.verify();
  }));

});
