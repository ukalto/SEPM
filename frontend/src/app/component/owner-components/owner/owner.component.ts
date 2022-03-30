import { Component, OnInit } from '@angular/core';
import { Owner } from '../../../dto/owner';
import { OwnerService } from 'src/app/service/owner.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {

  search = false;
  owners: Owner[];
  error: string = null;

  constructor(
    private service: OwnerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.reloadOwners();
  }

  reloadOwners() {
    this.service.getAll().subscribe({
      next: data => {
        console.log('received horses', data);
        this.owners = data;
      },
      error: error => {
        console.error('Error fetching owners', error.message);
        this.showError('Could not fetch owners: ' + error.message);
      }
    });
  }

  editOwner(owner : Owner): void {
    this.router.navigate(['owners/'+owner.ownerID+'/edit']).then();
  }

  newOwner(): void {
    this.router.navigate(['owners/create']).then();
  }

  deleteOwner(id: number): void {
    this.service.deleteOwner(id).subscribe({
      next: () => {
        this.owners = this.owners.filter(owner => owner.ownerID !== id)
      },
      error: error =>{
        console.error('Error deleting owners', error.message);
        this.showError('Could not delete owners: ' + error.message);
      }
    });
  }

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
