import {Component, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {Router} from '@angular/router';
import {HorseSearch} from "../../../dto/horse-search";

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {
  search: HorseSearch = {};
  horses: Horse[];
  error: string = null;

  constructor(
    private service: HorseService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.search = {
      name: null,
      description: null,
      birthdate: null,
      sex: null,
      ownerName: null
    };
    this.reloadHorses();
  }

  reloadHorses() {
    this.service.searchHorses(this.search).subscribe({
      next: data => {
        this.horses = data;
      },
      error: error => {
        console.error('Error fetching horses', error.message);
        this.showError('Could not fetch horses: ' + error.message);
      }
    });
  }

  editHorse(horse: Horse): void {
    this.router.navigate(['horses/' + horse.id + '/edit']).then();
  }

  newHorse(): void {
    this.router.navigate(['horses/create']).then();
  }

  detailsHorse(horse: Horse): void {
    this.router.navigate(['horses/' + horse.id + '/details']).then();
  }

  familyTree(horse: Horse): void {
    this.router.navigate(['horses/' + horse.id + '/familytree'], {queryParams: {generations: 2}}).then();
  }

  deleteHorse(id: number): void {
    this.service.deleteHorse(id).subscribe({
      next: () => {
        this.horses = this.horses.filter(horse => horse.id !== id)
      },
      error: error => {
        console.error('Error deleting horses', error.message);
        this.showError('Could not delete horses: ' + error.message);
      }
    });
  }

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }

  searchHorse() {
    if (this.search.sex == "EITHER") this.search.sex = null;
    this.reloadHorses();
  }
}
