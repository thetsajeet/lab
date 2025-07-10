import { NgIf } from '@angular/common';
import { Component, computed, input, signal } from '@angular/core';
import { DUMMY_USERS } from '../user/dummy-users';
import { TaskComponent } from './task/task.component';
import DUMMY_TASKS from './dummy-tasks';
import { User } from '../user/user.model';
import { NewTask, Task } from './task.model';
import { NewTaskComponent } from './new-task/new-task.component';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [NgIf, TaskComponent, NewTaskComponent],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  user = input.required<User>();
  tasks = signal<Task[]>(DUMMY_TASKS);
  showAddTask = signal<boolean>(false);

  userTasks = computed(() =>
    this.tasks().filter((t) => t.userId === this.user().id),
  );

  onCompleteTask(id: string) {
    this.tasks.set(this.tasks().filter((t) => t.id !== id));
  }

  onSubmit(t: NewTask) {
    this.tasks.update((prev) => [
      { ...t, id: new Date().getTime().toString(), userId: this.user().id },
      ...prev,
    ]);
    this.showAddTask.set(false);
  }
}
