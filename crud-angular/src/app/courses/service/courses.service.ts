import { Course } from '../model/course';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class CoursesService {
  private readonly url = "/api/courses";

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.url)
      .pipe(
        first(),
        tap(courses => console.log(courses))
      );
  }

  findById(id: string) {
    return this.httpClient.get<Course>(`${this.url}${id}`)
  }

  save(course: Partial<Course>) {
    return this.httpClient.post<Course>(this.url, course);
  }
}
