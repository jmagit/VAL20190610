import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-paises',
  templateUrl: './paises.component.html',
  styleUrls: ['./paises.component.css']
})
export class PaisesComponent implements OnInit {
  listado = [];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8001/paises').subscribe(
      data => this.listado = data['_embedded'],
      err => console.error("ERROR: " + err.message)
    );
  }

}
