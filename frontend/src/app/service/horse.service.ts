import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';
import {HorseSearch} from "../dto/horse-search";

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(private http: HttpClient) {
  }

  /**
   * Get all horses with given search properties (name,description,birthdate,sex,ownerName)
   * @return returns list of found horses
   */
  searchHorses(horseSearchDto?: HorseSearch): Observable<Horse[]> {
    const searchParams: URLSearchParams = new URLSearchParams();
    if (horseSearchDto?.name) searchParams.append("name", horseSearchDto.name);
    if (horseSearchDto?.description) searchParams.append("description", horseSearchDto.description);
    if (horseSearchDto?.birthdate) searchParams.append("birthdate", horseSearchDto.birthdate.toString());
    if (horseSearchDto?.sex) searchParams.append("sex", horseSearchDto.sex);
    if (horseSearchDto?.ownerName) searchParams.append("ownerName", horseSearchDto.ownerName);
    return this.http.get<Horse[]>(`${baseUri}?${searchParams.toString()}`);
  }

  /**
   * Get horse by id
   * @return returns horse with id
   */
  getHorse(id: number): Observable<Horse> {
    return this.http.get<Horse>(`${baseUri}/${id}`);
  }


  /**
   *  Returns a horse with all corresponding horses
   * @param id must exist, gens has to be > 0
   * @return returns an optional horse and goes throw one horse recursively to get all corresponding horses
   */
  getHorseWithGenerations(id: number, gens: number): Observable<Horse> {
    return this.http.get<Horse>(`${baseUri}/${id}/familytree?generations=${gens}`);
  }

  /**
   * Get 5 closest matches where birthdate is after all others, the horseName which represents the input is Like the
   * horse names in the database and the gender in the database matches the given gender
   * @param birthdate, horseName, gender has to be valid
   * @return if you pass the wrong arguments it will throw a HttpClientErrorException.NotFound
   */
  getHorsesAutocomplete(birthdate: Date, horseName: string, gender: string): Observable<Horse[]> {
    return this.http.get<Horse[]>(`${baseUri}/autocomplete?birthdate=${birthdate}&horseName=${horseName}&gender=${gender}`);
  }

  /**
   * Deletes horse via id
   * @params id has to be valid
   */
  deleteHorse(id: number): Observable<object> {
    return this.http.delete(`${baseUri}/${id}`);
  }

  /**
   * Updates horse
   * @params the name, the birthdate and sex of horse has to be set the rest can be null
   */
  editHorse(horse: Horse): Observable<object> {
    return this.http.put(baseUri, horse);
  }

  /**
   * Inserts horse
   * @params the name, the birthdate and sex of horse has to be set the rest can be null
   */
  insertHorse(horse: Horse): Observable<object> {
    return this.http.post(baseUri, horse);
  }
}
