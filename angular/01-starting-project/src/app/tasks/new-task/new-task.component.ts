import { Component, output } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { NewTask } from '../task.model';

@Component({
  selector: 'app-new-task',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './new-task.component.html',
  styleUrl: './new-task.component.css',
})
export class NewTaskComponent {
  cancel = output();
  submit = output<NewTask>();

  onCancel() {
    this.cancel.emit();
  }

  onSubmit(formData: NgForm) {
    if (formData.form.status !== 'VALID') return;

    this.submit.emit({ ...formData.form.value });
  }
}
