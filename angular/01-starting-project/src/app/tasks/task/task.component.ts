import { Component, input, output } from '@angular/core';
import { NewTask, Task } from '../task.model';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css',
})
export class TaskComponent {
  task = input.required<Task>();
  complete = output<string>();

  onClick() {
    this.complete.emit(this.task().id);
  }
}
