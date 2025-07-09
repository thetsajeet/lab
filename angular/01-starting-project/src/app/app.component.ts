import { Component } from '@angular/core';
import { HeaderComponent } from './header/header.component';
import { UserComponent } from './user/user.component';
import { DUMMY_USERS } from './user/dummy-users';
import { TasksComponent } from './tasks/tasks.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HeaderComponent, UserComponent, TasksComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  public users = DUMMY_USERS;
  public selectedUserId?: string;

  public handleSelectUser(data: string) {
    this.selectedUserId = data;
  }

  get selectedUser() {
    return DUMMY_USERS.find((u) => u.id === this.selectedUserId);
  }
}
