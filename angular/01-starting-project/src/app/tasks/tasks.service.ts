import { Injectable, signal } from '@angular/core';
import { NewTask, Task } from './task.model';
import DUMMY_TASKS from './dummy-tasks';

@Injectable({
  providedIn: 'root',
})
export class TasksService {
  private _tasks = signal<Task[]>(DUMMY_TASKS);

  public getUserTasks(userId: string) {
    return this._tasks().filter((t: Task) => t.userId === userId);
  }

  public removeTask(taskId: string) {
    this._tasks.update((prev) => prev.filter((t) => t.id !== taskId));
  }

  public addTask(t: NewTask, userId: string) {
    this._tasks.update((prev) => [
      { ...t, id: new Date().getTime().toString(), userId },
      ...prev,
    ]);
  }
}
