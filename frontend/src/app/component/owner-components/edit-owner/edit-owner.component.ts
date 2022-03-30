import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Owner} from 'src/app/dto/owner';
import {OwnerService} from 'src/app/service/owner.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-edit-owner',
  templateUrl: './edit-owner.component.html',
  styleUrls: ['./edit-owner.component.scss']
})
export class EditOwnerComponent implements OnInit {

  owner: Owner
  error: string = null;

  constructor(
    private service: OwnerService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.service.getAll().subscribe({
        next: (data) => {
          this.owner = data.filter(o => o.ownerID === +params.get("ownerID"))[0]
        },
        error: err => {
          console.error('Error fetching owners', err.message);
          this.showError('Could not fetch owners: ' + err.message);
        }
      });
    })
  }

  onSubmit(form: any) {
    if (form.valid) {
      console.log("submit", form)
      this.service.editOwner(this.owner).subscribe({
        next: () => {
          this.router.navigate(['owners']).then();
          alert("Successfully edited")
        },
        error: err => {
          console.error('Error fetching owners', err.message);
          this.showError('Could not fetch owners: ' + err.message);
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

  public vanishError(): void {
    this.error = null;
  }

  private showError(msg: string) {
    this.error = msg;
  }
}
