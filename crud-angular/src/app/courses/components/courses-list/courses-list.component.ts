import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Course } from '../../model/course';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss']
})
export class CoursesListComponent {
  @Input() courses: Course[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter();
  @Output() delete = new EventEmitter();
  @Output() deleteSelected = new EventEmitter();
  readonly displayedColumns = ["name", "category", "actions", "selection"];
  coursesChecked: Course[] = [];

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
}
