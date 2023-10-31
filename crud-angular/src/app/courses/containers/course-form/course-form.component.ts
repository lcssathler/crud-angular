import { Component } from '@angular/core';
import { NonNullableFormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { CoursesService } from '../../service/courses.service';
import { Course } from '../../model/course';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})

export class CourseFormComponent {

  form = this.formBuilder.group({
    _id: [""],
    name: [""],
    category: [""]
  })

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit() {
    const courseRoute: Course = this.route.snapshot.data['course'];

    this.form.setValue({
      _id: courseRoute._id,
      name: courseRoute.name,
      category: courseRoute.category
    })
  }

  onSubmit() {
    this.coursesService.save(this.form.value)
      .subscribe(result => this.successDialogMessage(), error => this.errorDialogMessage());
  }

  onCancel() {
    this.router.navigate([""], { relativeTo: this.route})
  }

  successDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Course saved successfully!" })
  }

  errorDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Error saving new course" })
  }
}
