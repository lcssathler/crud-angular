import { Component, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Course } from '../../model/course';
import { MatCheckbox, MatCheckboxModule } from '@angular/material/checkbox';
import { CategoryPipe } from '../../../shared/pipes/category.pipe';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';

@Component({
    selector: 'app-courses-list',
    templateUrl: './courses-list.component.html',
    styleUrls: ['./courses-list.component.scss'],
    standalone: true,
    imports: [MatTableModule, MatIconModule, MatButtonModule, MatCheckboxModule, CategoryPipe]
})
export class CoursesListComponent {
  @Input() courses: Course[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter();
  @Output() delete = new EventEmitter();
  @Output() deleteSelected = new EventEmitter();
  readonly displayedColumns = ["name", "category", "actions", "selection"];
  coursesChecked: Course[] = [];

  @ViewChildren('checkboxHeader') checkboxHeader!: MatCheckbox;
  @ViewChildren('checkboxCells') checkboxes!: QueryList<MatCheckbox>;


  constructor() {

  }

  onAdd() {
    this.add.emit(true);
  }

  onEdit(course: Course) {
    this.edit.emit(course);
  }

  onCheck(course: Course) {
    const index = this.coursesChecked.findIndex(item => item._id == course._id);
    if (index != -1) {
      this.coursesChecked.splice(index, 1);
    } else {
      this.coursesChecked.push(course);
    }
  }

  onDelete(course: Course) {
    this.delete.emit(course);
  }

  onDeleteSelected() {
    this.deleteSelected.emit(this.coursesChecked);
  }

  toggleCheckboxes(check: boolean,  courses: Course[]) {
    for (let i = 0; i < courses.length; i++) {
      const checkbox = this.checkboxes.get(i);
      checkbox!.checked = check;

      this.onCheck(courses[i]);
    }
  }
}
