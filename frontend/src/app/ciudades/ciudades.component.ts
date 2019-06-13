import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ciudades',
  templateUrl: './ciudades.component.html',
  styleUrls: ['./ciudades.component.css']
})
export class CiudadesComponent implements OnInit {
  page: object = {content: null};
  paginas: number[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.get(0);
  }

  get(nPag: number) {
    this.http.get(`http://localhost:8001/ciudades${nPag ? ('?page=' + nPag) : ''}`).subscribe(
      data => {
        this.page = data;
        this.paginas = [];
        for(let i: number = 0; i < data['totalPages']; this.paginas.push(i++));
      },
      err => console.error("ERROR: " + err.message)
    );
  }

}
