import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';

import { Course } from '../model/course';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root'
})

export class CoursesService {
  private readonly URL = "/api/courses";

  constructor(private httpClient: HttpClient) { }

  list(page = 0, pageSize = 10) {
    return this.httpClient.get<CoursePage>(this.URL, { params: { page, pageSize } }).pipe(first());
  }

  findById(id: string) {
    return this.httpClient.get<Course>(`${this.URL}/${id}`)
  }

  save(course: Partial<Course>) {
    if (course._id) {
      return this.update(course);
    }

    return this.create(course);
  }

  private create(course: Partial<Course>) {
    return this.httpClient.post<Course>(this.URL, course).pipe(first());
  }

  private update(course: Partial<Course>) {
    return this.httpClient.put<Course>(`${this.URL}/${course._id}`, course).pipe(first());
  }

  delete(id: string) {
    return this.httpClient.delete(`${this.URL}/${id}`).pipe(first());
  }

}
