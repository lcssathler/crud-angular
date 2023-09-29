import { Injectable } from '@angular/core';
import { Course } from '../model/course';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { delay, first, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class CoursesService {
  private readonly url = "/assets/courses.json";

  constructor(private httpClient: HttpClient) { }

  list(){
    return this.httpClient.get<Course[]>(this.url)
    .pipe(
      first(),
      delay(5000),
      tap(courses => console.log(courses))
    );
  }
}
