# Section 2

## How website is generated

> ng serve
> main.ts file is first looked into which has bootstrapApplication(AppComponent)
> bootstraps AppComponent and looks for the selector defined in the app.component.ts file in the index.html
> <app-root /> in index.html
> replaces app-root with the template defined in the templateUrl of app.component.ts
> Note: More than one bootstrapApplication() can be created if more selectors are added in index.html => eg: app1-root and app2-root

## Components

- components are standalone by default in v19+
- modular vs standalone:
  - modular: used only via an module such as custom module
  - standlone: don't need a module to be used -> introduced in v14

```ts
@Component({
  templateUrl: "", // template: ""
  styleUrl: "", // styles: "" // styleUrls: []
  standalone: true, // standalone: false
  imports: [], // import other components in standalone
  providers: [], // service providers to register in this component
})
export class Component {}
```

- to access data from .ts class
  - string interpolation for content - one way data binding => in .html `{{myvar}}`.
  - property binding for html attributes / properties eg: in .html `<img [src]="path" />`. Also `<img src="{{myVar}}" />` can use string interpolation too but not recommended.
  - attribute binding (similar to property binding) => `[attr.aria-valuenow]=""`
  - property binding targets underlying dom element's properties, if doesn't exists ignores.
  - attribute binding binds any attribute to the element used.
- to access data from html / user events:
  - event binding - `<... (click)="onClick($event)" />` => `$event` is from the DOM event. `onClick(e)` will be defined in .ts file.
  - Event binding listens to user events
- 2 way binding => `<input [(ngModel)]="myVar" />`

`ng g c <dir>/<comp-name>` -> creates component folder and files

### Loading

- Have a single entry point / bootstrap point eg: app-root and import all other elements in AppComponent.
- Single DOM Tree structure

### Using properties

- intead of using properties by defining in class and having that used dynamically like `{{myvar + "something else"}}`, use getters way.
- getters: `get something() {return myVar + "somethingElse"}` and use it as `{{something}}`
- this way it is easy to create dynamic values instead of appending / modifying in the html or separate variable for it.
- getters act the same way as regular variables except there is a `get` keyword and it's a function with return value
- setters are also used. eg: `set something(val: any) {myVar = val}` and use it as `something = 2`
- getters and setters are TS implementations.
- alternate form of using properties is by signals, where getters and setters are not needed.

#### Change Detection

- How does Angular detect changes in the component and update the view?
- Unlike React, Angular doesn't enforce using separate state management like `useState()`.
- Angular uses a package called `zone.js` that checks for all possible events such as user events, timer events, etc. and checks the view to see if there are any updates required.
- If so, it updates the view with the help of it.
- Con: Everytime something changes, the zone.js checks all the components for changes which might not be the most efficient way.

### Signals

- A second option introduced for change detection in v16 => signals
- Signals were introduced to make state management more efficient without needing zone.js package.
- Signals create a trackable subscription to where it's used and updates part of the UI that requires changes
- It only checks if that signal value changes and checks the corresponding DOM accordingly.
- More efficient than change detection with zone.js change detection
- `user = signal()` to create a signal with initial value
- `user.set()` to set a signal value
- `user.update(prev => prev.count + 1)` to update a signal based on previous
- `image = computed(() => user().image)` to create a signal from another signal
- `effect(() => console.log(user()))` effects are run when underlying signal value changes

### Input

- Pass data from parent to child components

```html
<user [name]="ajeet" />
```

```ts
@Input({required: false, alias: "custom", transform: ...}) name!: string; // tells whether required or not with an alias and transform
```

- with signals:

```ts
name = input.required<string>(); // or name = input<string>();
```

- can receive non-signal values as input.
- input signals are read-only signals. can't change from inside the component when passed from parent

### Output

- Send values from the component to parent component

```ts
@Output() select = new EventEmitter<string>();

handleSelect(val: string) {
  this.select.emit(val)
}
```

```html
<app (select)="onSelect(val)" />
```

- define onSelect in parent's .ts file
- with signals:

```ts
select = output<string>(); // creates an EE like @Output with strict type checking

handleSelect(val: string) {
  this.select.emit(val);
}
```

- same as @Output()
- output is not a signal, it creates an underlying EventEmitter
- why was it created? because no decorator is now required & shorter code syntax
- doesn't matter @Output() / output().
- Has stricter type checking

### Loops and Conditions

```tsx
@for (user of users; track user.id) {
  @if (user.id === 1) {
    <li></li>
  } @else if {

  } @else {

  }
  }
```

- track is similar to key in react. to know which to rerender when changed.
- alternate
- To use ngFor and ngIf use NgFor and NgIf from @angular/common
- Or import CommonModule
- no option for \*ng else if.
- instead use ngSwitch

```html
<li *ngFor=""></li>
<div *ngIf="isEnabled; else fallback"></div>
<ng-template #fallback>
  <p>fallback content</p>
</ng-template>
<div [ngSwitch]="isEnabled">
  <div *ngSwitchCase="true">1</div>
  <div *ngSwitchDefault>2</div>
</div>
```

### Dynamic Styling

1. [ngClass]="{isActive ? 'first class' : 'second class'}"
2. [ngStyle]="{'backgroundColor' : 'value'}"
3. [class.className]="isActive"

### Directives

- Directives don't have template.
- Enhances component with special features defined in the directive definition
- Eg: ngFor, ngIf, ngModel directives
- [(ngModel)]="" -> 2 way data binding. To use import FormsModule
- 2 way binding with signals:
  - val = signal('') and [(ngModel)]='val' no need to set as val(); angular automatically detects

### Form submissions

- <form (ngSubmit)="handleSubmit()"></form> with FormsModule

### Other features

- Content Projection: <ng-content></ng-content> to display content wrapped inside custom element
  - <ng-content></ng-content> & <app>hello</app>
- Pipes: Transform data in html without the need for functions in .ts files
  - {{name | uppercase }}
- Services: data management utility separately from components.
  - Dependency Injection: tells framework to create instance and have that instance in the component
  - Have only one instance of service throughout the application or specific components
  - constructor(private \_service: Service) {} in .ts file
  - @Injectable({providedIn: "root"}) // inject this service to root. now angular knows if service is used, where to find it and inject
  - alternate way of injecting instead of constructor => private service = inject(Service);
