import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HorseComponent } from './component/horse-components/horse/horse.component';
import { EditHorseComponent } from './component/horse-components/edit-horse/edit-horse.component';
import { OwnerComponent } from './component/owner-components/owner/owner.component';
import { InsertHorseComponent } from './component/horse-components/insert-horse/insert-horse.component';
import { InsertOwnerComponent } from './component/owner-components/insert-owner/insert-owner.component';
import { EditOwnerComponent } from './component/owner-components/edit-owner/edit-owner.component';
import { DetailsHorseComponent } from './component/horse-components/details-horse/details-horse.component';
import { FamilyTreeComponent } from './component/horse-components/family-tree/family-tree.component';

const routes: Routes = [
  { path: '', redirectTo: 'horses', pathMatch: 'full' },
  { path: 'horses', component: HorseComponent },
  { path: 'horses/:id/edit', component: EditHorseComponent },
  { path: 'horses/create', component: InsertHorseComponent },
  { path: 'horses/:id/details', component: DetailsHorseComponent },
  { path: 'horses/:id/familytree', component: FamilyTreeComponent },
  { path: 'owners', component: OwnerComponent },
  { path: 'owners/:ownerID/edit', component: EditOwnerComponent },
  { path: 'owners/create', component: InsertOwnerComponent },
  { path: '**', redirectTo: 'horses' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
