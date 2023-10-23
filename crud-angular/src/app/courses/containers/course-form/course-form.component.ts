import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormControl, FormGroup, NonNullableFormBuilder } from '@angular/forms';
import { CoursesService } from '../../service/courses.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})

export class CourseFormComponent {

  form = this.formBuilder.group({
    name: [""],
    category: [""]
  })

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private coursesService: CoursesService,
    private dialog: MatDialog) {

  }

  onSubmit() {
    this.coursesService.save(this.form.value)
      .subscribe(result => this.successDialogMessage(), error => this.errorDialogMessage());
  }

  onCancel() {

  }

  successDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Course saved successfully!" })
  }

  errorDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Error saving new course" })
  }
}
