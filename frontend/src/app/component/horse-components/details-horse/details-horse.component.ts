import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Horse} from 'src/app/dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-details-horse',
  templateUrl: './details-horse.component.html',
  styleUrls: ['./details-horse.component.scss']
})
export class DetailsHorseComponent implements OnInit {

  horse: Horse
  error: string = null;

  constructor(
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
          this.horseService.getHorse(this.horse.horseFather.id).subscribe(fatherData => {
            this.horse.horseFather = fatherData;
          });
          this.horseService.getHorse(this.horse.horseMother.id).subscribe(motherData => {
            this.horse.horseMother = motherData;
          });
          console.log(this.horse)
        },
        error: err => {
          console.error('Error fetching horses', err.message);
          this.showError('Could not fetch horses: ' + err.message);
        }
      });
    })
  }

  deleteHorse(): void {
    this.horseService.deleteHorse(this.horse.id).subscribe(value => {
      this.router.navigate(['horses']).then();
    });
  }

  editHorse(): void {
    this.router.navigate(['horses/' + this.horse.id + '/edit']).then();
  }

  detailsHorse(horse: Horse): void {
    this.router.navigate(['horses/' + horse.id + '/details']).then();
  }

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
