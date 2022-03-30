import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Owner } from '../dto/owner';

const baseUri = environment.backendUrl + '/owners';

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Get all owners
   * @return returns list of found owners
   */
  getAll(): Observable<Owner[]> {
    return this.http.get<Owner[]>(baseUri);
  }

  /**
   * Deletes owner via id
   * @params ownerID has to be valid
   */
  deleteOwner(ownerID: number): Observable<object> {
    return this.http.delete(`${baseUri}/${ownerID}`);
  }

  /**
   * Updates owner
   * @params the prename and the surname of o has to be set email can be null
   */
  editOwner(owner: Owner): Observable<object>{
    return this.http.put(baseUri, owner);
  }

  /**
   * Inserts owners
   * @params the prename and the surname of o has to be set email can be null
   */
  insertOwner(owner: Owner): Observable<object>{
    return this.http.post(baseUri, owner);
  }

  /**
   * Get 5 closest named existing owners
   * @param name has to be valid
   */
  getOwnersAutocomplete(name: string): Observable<Owner[]> {
    return this.http.get<Owner[]>(`${baseUri}/autocomplete?name=${name}`);
  }
}
