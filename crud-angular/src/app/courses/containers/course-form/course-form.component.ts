import { Component } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';

import { ErrorDialogComponent } from '../../../shared/components/error-dialog/error-dialog.component';
import { Course } from '../../model/course';
import { CoursesService } from '../../service/courses.service';
import { Lesson } from '../../model/lesson';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})

export class CourseFormComponent {
  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router) {

  }

  ngOnInit() {
    const courseRoute: Course = this.route.snapshot.data['course'];

    this.form = this.formBuilder.group({
      _id: [courseRoute._id],
      name: [courseRoute.name, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      category: [courseRoute.category, [Validators.required]],
      lessons: this.formBuilder.array(this.retrieveLessons(courseRoute), Validators.required)
    });
  }

  private retrieveLessons(course: Course) {
    const lessons = [];
    if (course?.lessons) {
      course.lessons?.forEach(lesson => lessons.push(this.createLesson(lesson)));
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  private createLesson(lesson: Lesson = { id: '', name: '', url: '' }) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      url: [lesson.url, [Validators.required, Validators.minLength(10), Validators.maxLength(11)]]
    })
  }

  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  onAddNewLesson(): void {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.push(this.createLesson());
  }

  onRemoveLesson(index: number): void {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    lessons.removeAt(index);
  }

  isFormArrayValid(): boolean {
    const lessons = this.form.get('lessons') as UntypedFormArray;
    return !lessons.valid && lessons.getError('required') && lessons.touched;
  }

  onSubmit() {
    if (this.form.valid) {
      this.coursesService.save(this.form.value)
        .subscribe(result => this.successDialogMessage(), error => this.errorDialogMessage("Error saving new course"));
    } else {
      this.errorDialogMessage("Invalid form");
    }

    // TODO: refresh page or clear course's controls fields
  }

  onCancel() {
    this.router.navigate([""], { relativeTo: this.route } );
  }

  successDialogMessage() {
    this.dialog.open(ErrorDialogComponent, { data: "Course saved successfully!" });
  }

  errorDialogMessage(msg: string) {
    this.dialog.open(ErrorDialogComponent, { data: msg })
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
