import { Component, inject, input, output } from '@angular/core';
import { NewTask, Task } from '../task.model';
import { CardComponent } from '../../shared/card/card.component';
import { DatePipe } from '@angular/common';
import { TasksService } from '../tasks.service';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CardComponent, DatePipe],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css',
})
export class TaskComponent {
  task = input.required<Task>();
  tasksService = inject(TasksService);

  onClick() {
    this.tasksService.removeTask(this.task().id);
  }
}
