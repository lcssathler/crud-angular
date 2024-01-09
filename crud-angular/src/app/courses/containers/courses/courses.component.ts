import { Component, OnInit, ViewChild } from '@angular/core';
import { Course } from '../../model/course';
import { CoursesService } from '../../service/courses.service';
import { Observable, catchError, of, tap } from 'rxjs';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';

import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { CoursePage } from '../../model/course-page';
import { MatPaginator, PageEvent, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CoursesListComponent } from '../../components/courses-list/courses-list.component';
import { AsyncPipe } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';

@Component({
    selector: 'app-courses',
    templateUrl: './courses.component.html',
    styleUrls: ['./courses.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatToolbarModule, CoursesListComponent, MatPaginatorModule, MatProgressSpinnerModule, AsyncPipe]
})
export class CoursesComponent implements OnInit {
  courses$: Observable<CoursePage> | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  pageIndex = 0;
  pageSize = 10;

  constructor(
    private coursesService: CoursesService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.refresh();
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.courses$ = this.coursesService.list(pageEvent.pageIndex, pageEvent.pageSize)
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(error => {
          this.onMsg("Error loading courses");
          return of({ courses: [], totalElements: 0, totalPages: 0 });
        })
      );
  }

  ngOnInit() {

  }

  onAdd() {
    this.router.navigate(["new"], { relativeTo: this.route });
  }

  onEdit(course: Course) {
    this.router.navigate(["edit", course._id], { relativeTo: this.route })
  }

  onDelete(course: Course) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: "Do you want to remove this course?",
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.delete(course._id)
          .subscribe(
            (success) => {
              this.refresh();
              this.onMsg("Course deleted successfully!")
            },
            (error) => this.onMsg("Error deleting course")
          );
      }
    })
  }

  onDeleteSelected(coursesToDelete: Course[]) {
    console.log(coursesToDelete);

    if (coursesToDelete.length == 0) {
      this.onMsg("None course selected to delete");
      return;
    }

    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: `Do you want to remove ${coursesToDelete.length} courses?`,
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.coursesService.deleteSelected(coursesToDelete)
          .subscribe((success) => {
            this.refresh();
            this.onMsg("Courses deleted successfully!")
          },
            (error) => this.onMsg("Error deleting courses")
          );
      }
    })
  }

  onMsg(msg: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: msg
    });
  }
}
