import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor(private httpClient: HttpClient) { }

  list(): Course[] {
    return [
      { _id: "4", name: "Web Designer", category: "UX/UI" }
    ]
  }
}
