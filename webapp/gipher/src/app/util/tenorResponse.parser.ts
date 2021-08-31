import { Observable } from "rxjs";
import { Gif } from "../models/Gif";
import { TenorResponse } from "../models/TenorResponse";

export function parseTenorResponseForGifs(response: Observable<TenorResponse>): Gif[] {
    let gifs: Gif[] = [];

    response.subscribe(data => {
        for (let gif of data.results) {
            gifs.push({
                id: gif.id,
                itemurl: gif.itemurl,
                media: gif.media[0]
            });
        }
    })

    return gifs;
}
