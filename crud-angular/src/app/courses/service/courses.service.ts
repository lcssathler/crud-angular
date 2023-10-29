import { Course } from '../model/course';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { first, tap } from 'rxjs';
import { Injectable } from '@angular/core';
import { core } from '@angular/compiler';

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
    return this.httpClient.get<Course>(`${this.url}/${id}`)
  }

  save(course: Partial<Course>) {
    if (course._id) {
      console.log("update");
      return this.update(course);
    }

    console.log("create");
    return this.create(course);
  }


  private create(course: Partial<Course>) {
    return this.httpClient.post<Course>(this.url, course).pipe(first());
  }

  private update(course: Partial<Course>) {
    return this.httpClient.put<Course>(`${this.url}/${course._id}`, course).pipe(first());
  }
}
