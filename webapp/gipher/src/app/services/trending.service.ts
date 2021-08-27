import { Injectable } from '@angular/core';
import { Gif } from '../models/Gif';

import { PLACEHOLDER_GIFS } from "../../../data/gifs";

@Injectable({
  providedIn: 'root'
})
export class TrendingService {

  constructor() { }

  getGifs(max?: number): Gif[] {
    return PLACEHOLDER_GIFS as Gif[];
  }
}
