import {
  Component,
  Input,
  computed,
  input,
  output,
  signal,
} from '@angular/core';
import { DUMMY_USERS } from './dummy-users';
import { User } from './user.model';

const USER_IMAGE_SRC = 'assets/users';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
})
export class UserComponent {
  // public selectedUser=DUMMY_USERS[this.randomIndex];
  // public selectedUser = signal(DUMMY_USERS[this.randomIndex]);

  public selectedUser = input.required<User>({
    alias: 'user',
  });
  public selectUser = output<string>();
  public selected = input<boolean>(false);

  // public get userImageSrc(): string {
  //   return `${USER_IMAGE_SRC}/${this.selectedUser.avatar}`;
  // }

  // userImageSrc = computed(
  //   () => `${USER_IMAGE_SRC}/${this.selectedUser().avatar}`,
  // );

  userImageSrc = computed(
    () => `${USER_IMAGE_SRC}/${this.selectedUser().avatar}`,
  );

  // private get randomIndex(): number {
  //   return Math.floor(Math.random() * DUMMY_USERS.length);
  // }

  /**
   * onClick
   * @param event
   */
  // public onClick(event: any) {
  //   this.selectedUser.set(DUMMY_USERS[this.randomIndex]);
  // }

  onSelectUser() {
    this.selectUser.emit(this.selectedUser().id);
  }
}
