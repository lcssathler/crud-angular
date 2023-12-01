import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})

export class CoursesService {
  private readonly url = "/api/courses";

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Course[]>(this.url)
      .pipe(first());
  }

  findById(id: string) {
    return this.httpClient.get<Course>(`${this.url}/${id}`)
  }

  save(course: Partial<Course>) {
    if (course._id) {
      return this.update(course);
    }

    return this.create(course);
  }

  private create(course: Partial<Course>) {
    return this.httpClient.post<Course>(this.url, course).pipe(first());
  }

  private update(course: Partial<Course>) {
    return this.httpClient.put<Course>(`${this.url}/${course._id}`, course).pipe(first());
  }

  delete(id: string) {
    return this.httpClient.delete(`${this.url}/${id}`).pipe(first());
  }

}
