import { Component, OnInit } from '@angular/core';
import { Owner } from 'src/app/dto/owner';
import { Router } from '@angular/router';
import { OwnerService } from 'src/app/service/owner.service';


@Component({
  selector: 'app-insert-owner',
  templateUrl: './insert-owner.component.html',
  styleUrls: ['./insert-owner.component.scss']
})
export class InsertOwnerComponent implements OnInit {

  private owner: Owner
  error: string = null;

  constructor(
    private service: OwnerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.owner = { ownerID: null, prename: "", surname: "", email: "" };
  }

  onSubmit(form: any) {
    if (form.valid) {
      this.service.insertOwner(this.owner).subscribe({
        next: () => {
          this.router.navigate(['owners']).then();
          alert("Successfully inserted");
        },
        error: err => {
          console.error('Error fetching owners', err.message);
          this.showError('Could not fetch owners: ' + err.message);
        }
      });
    }
    else{
      let x = document.getElementById("snackbar");
      x.className = "show";
      setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    }
  }

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
