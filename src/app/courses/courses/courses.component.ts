import { Component, OnInit } from '@angular/core';
import { Course } from '../model/course';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  courses: Course[] = [
    { _id: "1", name: "Expert Front-End Developer", category: "Front-End" },
    { _id: "1", name: "Full Stack Java Developer", category: "Full Stack" },
    { _id: "1", name: "How to have a healthier life", category: "Health" }
  ];
  displayedColumns = ["name", "category"];

  constructor() {

  }

  ngOnInit(): void {

  }
}
