import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PLACEHOLDER_GIFS } from 'data/gifs';

import { GifGridComponent } from '../gif-grid/gif-grid.component';
import { Gif } from '../models/Gif';

describe('GifGridComponent', () => {
  let component: GifGridComponent;
  let fixture: ComponentFixture<GifGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GifGridComponent ],
      imports: []
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GifGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should show a grid of gifs", () => {
    component.gifs = PLACEHOLDER_GIFS as Gif[];
    component.ngOnInit();
    fixture.detectChanges();
    let numOfGifs = fixture.nativeElement.querySelectorAll("img").length;
    expect(numOfGifs).toEqual(component.gifs.length);
  })
});
