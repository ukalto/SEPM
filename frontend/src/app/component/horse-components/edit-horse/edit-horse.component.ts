import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Horse} from 'src/app/dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {Router} from '@angular/router';
import {Owner} from 'src/app/dto/owner';
import {OwnerService} from 'src/app/service/owner.service';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {Observable, Subject, OperatorFunction, tap, catchError, of, switchMap} from 'rxjs';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';

@Component({
  selector: 'app-edit-horse',
  templateUrl: './edit-horse.component.html',
  styleUrls: ['./edit-horse.component.scss']
})
export class EditHorseComponent implements OnInit {

  horse: Horse;
  error: string = null;
  selectedOwner: Owner;
  selectedFather: Horse;
  selectedMother: Horse;
  loadingOwners: boolean;
  loadingOwnersFailed: boolean;
  loadingFathers: boolean;
  loadingFathersFailed: boolean;
  loadingMothers: boolean;
  loadingMothersFailed: boolean;
  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  constructor(
    private ownerService: OwnerService,
    private horseService: HorseService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.horseService.getHorse(+params.get("id")).subscribe({
        next: (data) => {
          this.horse = data;
          console.log(this.horse)
          this.selectedOwner = this.horse.owner;
          this.horseService.getHorse(this.horse.horseFather.id).subscribe(fatherData => {
            this.selectedFather = fatherData;
          });
          this.horseService.getHorse(this.horse.horseMother.id).subscribe(motherData => {
            this.selectedMother = motherData;
          });
        },
        error: err => {
          console.error('Error fetching horses', err.message);
          this.showError('Could not fetch horses: ' + err.message);
        }
      });
    })
  }

  onSubmit(form: any) {
    if (form.valid) {
      if (typeof this.selectedOwner == "string") this.horse.owner = null;
      else this.horse.owner = this.selectedOwner;
      if (typeof this.selectedFather == "string") this.horse.horseFather = null;
      else this.horse.horseFather = this.selectedFather;
      if (typeof this.selectedMother == "string") this.horse.horseMother = null;
      else this.horse.horseMother = this.selectedMother;
      this.horseService.editHorse(this.horse).subscribe({
        next: () => {
          this.router.navigate(['horses']).then();
          alert("Successfully edited");
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

  deleteHorse(): void {
    this.horseService.deleteHorse(this.horse.id).subscribe(value => {
      this.router.navigate(['horses']).then();
    });
  }

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
