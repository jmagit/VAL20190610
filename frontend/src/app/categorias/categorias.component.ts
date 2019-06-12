import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-categorias',
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.css']
})
export class CategoriasComponent implements OnInit {
  listado = [];
  peliculas: any = [];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8002/categorias').subscribe(
      data => this.listado = data['content'],
      err => console.error("ERROR: " + err.message)
    );
  }
  selecciona(id: number) {
    this.http.get(`http://localhost:8002/categorias/${id}/peliculas`).subscribe(
      data => this.peliculas = data,
      err => console.error("ERROR: " + err.message)
    );

  }

}
