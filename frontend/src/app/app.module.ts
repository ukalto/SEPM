import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './component/header/header.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { HorseComponent } from './component/horse-components/horse/horse.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EditHorseComponent } from './component/horse-components/edit-horse/edit-horse.component';
import { OwnerComponent } from './component/owner-components/owner/owner.component';
import { InsertHorseComponent } from './component/horse-components/insert-horse/insert-horse.component';
import { EditOwnerComponent } from './component/owner-components/edit-owner/edit-owner.component';
import { InsertOwnerComponent } from './component/owner-components/insert-owner/insert-owner.component';
import { DetailsHorseComponent } from './component/horse-components/details-horse/details-horse.component';
import { FamilyTreeComponent } from './component/horse-components/family-tree/family-tree.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseComponent,
    EditHorseComponent,
    OwnerComponent,
    InsertHorseComponent,
    EditOwnerComponent,
    InsertOwnerComponent,
    DetailsHorseComponent,
    FamilyTreeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
