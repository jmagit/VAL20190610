import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CategoriasComponent } from './categorias/categorias.component';
import { HomeComponent } from './home/home.component';
import { PaisesComponent } from './paises/paises.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'categorias', component: CategoriasComponent },
  { path: 'paises', component: PaisesComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
