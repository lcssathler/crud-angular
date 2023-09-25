import { Component, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { CoursesService } from '../service/courses.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  coursesService: CoursesService;

  courses: Course[] = [];

  displayedColumns = ["name", "category"];

  constructor() {
    this.coursesService = new CoursesService;
    this.courses = this.coursesService.list();
  }

  ngOnInit(): void {

  }
}
