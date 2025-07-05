# Angular Notes

## Section 1

### Introduction

- Angular is s JS framework to build fast and reliable web applications
- Why Angular?
  - JS framework that supports declarative code over imperative style
  - Comes with a suite of utilities designed for production and development such as debugging, compiler, etc.
  - Separaton of concerns with components
  - TS first code
- AngularJS vs Angular
  - AngularJS in v1 and built on top of JS.
  - Angular is from v2-20(latest) built on top of TS

## Section 2

### How website is generated

```bash
ng serve #1
# main.ts file is first looked into which has bootstrapApplication(AppComponent)
# bootstraps AppComponent and looks for the selector defined in the app.component.ts file in the index.html
# <app-root /> in index.html
# replaces app-root with the template defined in the templateUrl of app.component.ts
```

> Note: More than one bootstrapApplication() can be created if more selectors are added in index.html => eg: app1-root and app2-root

### Components

- components are standalone by default in v19+
- modular vs standalone:
  - modular: used only via an module such as custom module
  - standlone: don't need a module to be used -> introduced in v14

// TODO: Add component definition

```ts
@Component({
  templateUrl: "", // template: ""
  styleUrl: "", // styles: "" // styleUrls: []
  standalone: true, // standalone: false
  imports: [], // import other components in standalone
})
export class Component {}
```

- to access data from .ts class
  - 1. string interpolation for content - one way data binding => in .html `{{myvar}}`
  - 2. property binding for html attributes / properties eg: in .html `<img [src]="path" />`. Can use string interpolation too but not recommended
  - 3. attribute binding (similar to property binding) => `[attr.aria-valuenow]=""` => property binding targets underlying dom element. attribute binding binds to the element used.
  - 4. 2 way binding => [(ngModel)]="var"
- to access data from html / user events:
  - 1. event binding - `<... (click)="onClick($event)" />` => `$event` is from the DOM event. `onClick` will be defined in .ts file : Event binding listens to user events

> Note: Property and attribute binding are different. Property targets underlying DOM while attribute targets the element's attribute
> Property -> tries to find underlying property, attribute -> just attaches to element without finding property in DOM

- intead of using properties by defining in class and having that used dynamically like `{{myvar + "something else"}}`, use getters way.
- getters: `get something() {return myVar + "somethingElse"}` and use it as `{{something}}`
  - this way it is easy to create dynamic values instead of appending / modifying in the html or separate variable for it. getters act the same way as regular variables except there is a `get` keyword and it's a function with return value

### Change Detection

- How does Angular detect changes in the component and update the view?
- Unlike React, Angular doesn't enforce using separate state management like `useState()`
- Angular uses a package called zone.js that checks for all possible events such as user events, timer events, etc. and checks the view to see if there are any updates required. If so, it updates the view with the help of it.
- Con: Everytime something changes, the zone.js checks all the components for changes which might not be the most efficient way.

### Signals

- A second option introduced for change detection in v16 => signals
- Signals were introduced to make state management more efficient which zone.js didn't provide

```ts
myVar = signal("hello");
// {{myVar()}} to use it in html -> requires function call since myVar is a signal and not a property
myVar.set("Hello World"); // to update value use .set()
something = computed(() => myVar() + "!"); // instead of getters to dynamically set values of computed properties, use computed which will take a function and generate another signal
```

- Signals create a trackable subscription to where it's used.
- It only checks if that signal value changes and checks the corresponding DOM accordingly.
- more efficient than zone.js change detection

### Input

- Set custom attributes to the component

```html
<app [custom]="ajeet" />
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
- why was it created? because no decorator required & shorter code syntax
- doesn't matter @Output() / output() has stricter type checking

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
- no option for else if. instead use ngSwitch

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
