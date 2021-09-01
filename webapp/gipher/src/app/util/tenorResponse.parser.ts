import { Observable } from "rxjs";
import { Gif } from "../models/Gif";
import { TenorResponse } from "../models/TenorResponse";

export function parseTenorResponseForGifs(response:TenorResponse, ): Gif[] {
    let gifs: Gif[] = [];

    for (let gif of response.results) {
        gifs.push({
            id: gif.id,
            itemurl: gif.itemurl,
            media: gif.media[0],
            favorite:false
        });
    }

    return gifs;
}
