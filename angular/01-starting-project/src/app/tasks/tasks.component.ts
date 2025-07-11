import { NgIf } from '@angular/common';
import { Component, computed, inject, input, signal } from '@angular/core';
import { DUMMY_USERS } from '../user/dummy-users';
import { TaskComponent } from './task/task.component';
import DUMMY_TASKS from './dummy-tasks';
import { User } from '../user/user.model';
import { NewTask, Task } from './task.model';
import { NewTaskComponent } from './new-task/new-task.component';
import { TasksService } from './tasks.service';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [NgIf, TaskComponent, NewTaskComponent],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  user = input.required<User>();
  showAddTask = signal<boolean>(false);
  private tasksService = inject(TasksService);

  userTasks = computed(() => this.tasksService.getUserTasks(this.user().id));
}
