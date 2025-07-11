import { Component, output, inject, input } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NewTask } from '../task.model';
import { TasksService } from '../tasks.service';

@Component({
  selector: 'app-new-task',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './new-task.component.html',
  styleUrl: './new-task.component.css',
})
export class NewTaskComponent {
  userId = input.required<string>();
  close = output();
  tasksService = inject(TasksService);

  onCancel() {
    this.close.emit();
  }

  onSubmit(formData: NgForm) {
    if (formData.form.status !== 'VALID') return;

    this.tasksService.addTask({ ...formData.form.value }, this.userId());

    this.close.emit();
  }
}
