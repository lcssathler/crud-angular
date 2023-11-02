import { Component } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';

import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { Course } from '../../model/course';
import { CoursesService } from '../../service/courses.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})

export class CourseFormComponent {

  form = this.formBuilder.group({
    _id: [""],
    name: ["",
      [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)
      ]
    ],
    category: ["", [Validators.required]]
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
    this.router.navigate([""], { relativeTo: this.route })
  }

  successDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Course saved successfully!" })
  }

  errorDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Error saving new course" })
  }

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if (field?.hasError('required')) {
      return "Required field";
    }

    if (field?.hasError('minlength')) {
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength'] : 5
      return `The minimum number of characters must be ${requiredLength}`;
    }

    if (field?.hasError('maxlength')) {
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength'] : 100
      return `The maximum number of characters must be ${requiredLength}`;
    }

    return "Invalid field";
  }
}
