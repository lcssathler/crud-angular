import { Injectable } from '@angular/core';
import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  constructor() { }

  list(): Course[] {
    return [
      {_id: "4", name: "Web Designer", category: "UX/UI"}
    ]
  }
}
