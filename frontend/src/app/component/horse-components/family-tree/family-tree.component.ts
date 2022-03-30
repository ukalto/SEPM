import {Component, OnInit} from '@angular/core';
import {HorseService} from '../../../service/horse.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Horse} from '../../../dto/horse';


@Component({
  selector: 'app-family-tree',
  templateUrl: './family-tree.component.html',
  styleUrls: ['./family-tree.component.scss']
})
export class FamilyTreeComponent implements OnInit {
  horse: Horse;
  generations = 2;

  constructor(private horseService: HorseService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getHorseWithGenerations();
  }

  getHorseWithGenerations(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.activatedRoute.queryParamMap.subscribe(queryParams => {
        const generations: number = +queryParams.get("generations") || 2;
        this.generations = generations;
        this.horseService.getHorseWithGenerations(+params.get("id"), generations).subscribe({
          next: (data) => {
            this.horse = data;
          }
        })
      })
    })
  }

  deleteHorse(horseId: number): void {
    this.horseService.deleteHorse(horseId).subscribe(value => {
      if (horseId === this.horse.id) {
        this.router.navigate(['horses']).then();
      } else {
        this.getHorseWithGenerations();
      }
    });
  }

  generationsChanged(event: Event): void {
    this.router.navigate([], {queryParams: {generations: this.generations}}).then();
  }

  detailsHorse(horse: Horse): void {
    this.router.navigate(['horses/' + horse.id + '/details']).then();
  }
}
