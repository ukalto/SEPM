import {Component, OnInit, ViewChild} from '@angular/core';
import {Horse} from 'src/app/dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {Router} from '@angular/router';
import {catchError, Observable, of, OperatorFunction, Subject, switchMap, tap} from "rxjs";
import {Owner} from "../../../dto/owner";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {OwnerService} from "../../../service/owner.service";


@Component({
  selector: 'app-insert-horse',
  templateUrl: './insert-horse.component.html',
  styleUrls: ['./insert-horse.component.scss']
})
export class InsertHorseComponent implements OnInit {
  horse: Horse
  error: string = null;
  selectedOwner: Owner;
  loadingOwners: boolean;
  loadingOwnersFailed: boolean;
  selectedFather: Horse;
  selectedMother: Horse;
  loadingFathers: boolean;
  loadingFathersFailed: boolean;
  loadingMothers: boolean;
  loadingMothersFailed: boolean;
  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  constructor(
    private horseService: HorseService,
    private ownerService: OwnerService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.horse = {
      id: null,
      name: "",
      description: "",
      birthdate: null,
      sex: "",
      owner: null,
      horseFather: null,
      horseMother: null
    };
  }

  onSubmit(form: any) {
    if (form.valid) {
      this.horseService.insertHorse(this.horse).subscribe({
        next: () => {
          this.router.navigate(['horses']).then();
          alert("Successfully inserted");
        },
        error: err => {
          console.error('Error fetching horses', err.message);
          this.showError('Could not fetch horses: ' + err.message);
        }
      });
    } else {
      let x = document.getElementById("snackbar");
      x.className = "show";
      setTimeout(function () {
        x.className = x.className.replace("show", "");
      }, 3000);
    }
  }

  searchOwner: OperatorFunction<string, readonly Owner[]> = (text$: Observable<string>) =>
      text$.pipe(
          debounceTime(300),
          distinctUntilChanged(),
          tap(() => this.loadingOwners = true),
          switchMap(term => this.ownerService.getOwnersAutocomplete(term).pipe(
              tap(() => this.loadingOwnersFailed = false),
              catchError(() => {
                this.loadingOwnersFailed = true;
                return of([]);
              })),
          ),
          tap(() => this.loadingOwners = false),
      )

  ownerTypeaheadFormatter = (owner: Owner) => `${owner.prename} ${owner.surname}`;

  searchFather: OperatorFunction<string, readonly Horse[]> = (text$: Observable<string>) =>
      text$.pipe(
          debounceTime(300),
          distinctUntilChanged(),
          tap(() => this.loadingFathers = true),
          switchMap(text => this.horseService.getHorsesAutocomplete(this.horse.birthdate, text, "MALE").pipe(
              tap(() => this.loadingFathersFailed = false),
              catchError(() => {
                this.loadingFathersFailed = true;
                return of([]);
              })),
          ),
          tap(() => this.loadingFathers = false),
      )

  searchMother: OperatorFunction<string, readonly Horse[]> = (text$: Observable<string>) =>
      text$.pipe(
          debounceTime(300),
          distinctUntilChanged(),
          tap(() => this.loadingMothers = true),
          switchMap(text => this.horseService.getHorsesAutocomplete(this.horse.birthdate, text, "FEMALE").pipe(
              tap(() => this.loadingMothersFailed = false),
              catchError(() => {
                this.loadingMothersFailed = true;
                return of([]);
              })),
          ),
          tap(() => this.loadingMothers = false),
      )

  parentTypeaheadFormatter = (horse: Horse) => `${horse.name}`;

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
