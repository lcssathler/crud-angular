import { Component, OnInit } from '@angular/core';
import { Course } from '../../model/course';
import { CoursesService } from '../../service/courses.service';
import { Observable, catchError, of } from 'rxjs';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';

import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {
  courses$: Observable<Course[]> | null = null;

  constructor(
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.refresh();
  }

  refresh() {
    this.courses$ = this.coursesService.list()
      .pipe(
        catchError(error => {
          this.onMsg("Error loading courses")
          return of([]);
        })
      );
  }


  onAdd() {
    this.router.navigate(["new"], { relativeTo: this.route });
  }

  onEdit(course: Course) {
    this.router.navigate(["edit", course._id], { relativeTo: this.route })
  }

  onDelete(course: Course) {
    this.coursesService.delete(course._id)
      .subscribe(
        (success) => {
          this.refresh();
          this.onMsg("Course deleted successfully!")
        },
        (error) => this.onMsg("Error deleting course")
      );
  }

  onMsg(msg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: msg
    });
  }

  ngOnInit() {

  }
}
