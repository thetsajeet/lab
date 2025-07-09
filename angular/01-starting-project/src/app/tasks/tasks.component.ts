import { NgIf } from '@angular/common';
import { Component, input } from '@angular/core';
import { DUMMY_USERS } from '../user/dummy-users';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [NgIf],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export class TasksComponent {
  user = input.required<(typeof DUMMY_USERS)[0]>();
}
