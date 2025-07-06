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

## Section 2

### Angular Modules

- From angular v19 -> all components by default are standalone components

```ts
@NgModule({
  declarations: [], // declare all non-standalone components and directives you want to declare
  bootstrap: [AppComponent],
  imports: [BrowserModule, CommonModule], // import modules + standalone components / directives if present
})
export class AppModule {}
```

- with modular approach -> loading becomes such a way that you have to do `platformBrowserDynamic().bootstrapModule(AppModule)`
- this way first loads appmodule -> appcomponent
- BrowserModule should only be imported once in root.
- You can use CommonModule if you need certain modules to be used across. Eg: DatePipe

## Section 6

### Splitting Components

- Separation of concerns: every component should only do one thing (SRP in SOLID)
- Simplicity & Code Colocation
- Trade off: Separation of concerns vs Code Colocation

### Extending Built-in components

- Instead of having <app-button><button></button></app-button> in DOM, we can extend built-in components in angular.
- Selectors in Components:
  - Element selector => selector: "app-button",
  - Attribute selector => selector: "[app-button]"
  - Class selectors => selector: ".app-button"
  - different permutations of these.

### Content Projection

- done using <ng-content />
- can have multiple slots with select

```html
in definition <ng-content select=".icon" /> <ng-content />

in someother place
<app>
  <span>hello</span>
  <span class="icon">icon</span>
</app>

to avoid span duplications
<app>
  hello
  <span ngProjectAs="icon">icon</span> // span won't be included, only used to
  project
</app>

to have fallback content

<ng-content class="icon">-></ng-content> // checks for selector. if not present,
then uses fallback content. latest feature
```

### Multielement selectors

selector: "app[button], app2[button]" // either <app button /> or <app2 button />

### ViewEncapsulation

- styles won't work on ng-content since it's a placeholder.
- Encapsulation: ViewEncapsulation.Emulated -> shadow dom feature to attach hidden dom structures to dom elements. eg: video element. Emulated uses this shadow dom feature for its own.
- ViewEncapsulation.ShadowDom -> real shadow dom browser feature instead of emulated, but is not supported by all browsers
- ViewEncapsulation.None -> removes shadow dom. so styles setup wil be available to all elements. no hidden structure

### Host

- :host apply css styles on the host element -> css feature
- if view encapsulation is none -> you can use it as regular component attributes like class, style and if defined in css can have effect
- instead of adding, add host: {} to component definition. can be when encapsulation is not None too

```ts
@Component({
  host: {
    class: "control", // adds to <app/> the host element
    '(click)': "onClick()"
  }
})
```

- @HostBinding('class') className = "control" // adds class attribute to host element. not encouraged
- @HostListener('dblclick') onDblClick() {} // adds event listener to host
- el = inject(ElementRef) // or via constructor to access the host element

### Component Lifecycle

- implements OnInit, OnDestroy, etc -> Lifecycle interface
- constructor -> when angular instantiates
- ngOnChanges(changes: SimpleChanges) {} -> after constructor. Only checks for all input changes. SimpleChange: {currentValue, previousValue, firstChange}
- ngOnInit -> angular has instantiated with inputs
- ngDoCheck -> check for changes: for change detection
- ngAfterViewInit -> view init (template / html)
- ngAfterContentInit -> content init (content / ng-content)
- ngAfterViewChecked -> view checked
- ngAfterContentChecked -> content checked
- ngOnDestroy -> before component is destroyed -> unsubscribe
- v16+ -> destroyRef = inject(DestroyRef) and this.destroyRef.onDestroy(() => {})

### Template Variables

- in any element use `#myVar` and when in functions use it as `(click)="handleClick(myVar)"` you get the html element in the handleClick function
- template var in custom component gives access to instance of component: gives access to methods, data, etc.
- Access elements in .ts via template variables using @ViewChild()
- can take in ClassInstance, or 'myVar' template variable without #

```ts
@ViewChild('myVar') el: ElementRef<HTMLFormElement>;
```

- to access multiple children: @ViewChildren('myVar') elAr: ElementRef<>[];
- private el = viewChild<>('myVar'); // v17.3 signal
- private el = viewChild.required<>('myVar')
- @ContentChild to access content projected template, similar to ViewChild
- contentChild

## Section 11

### RxJS

- package that angular uses for Observables
- Observable -> stream of data
- Subjects are a type of observable
- eg: `subscription = interval(1000).subscribe({next: (val) => {}, error: (err) => {}, complete: () => {}})`
- Observables are subscribable objects that when subscribed listens to changes and provides the val in next function everytime val changes, and error if error occurs, and complete when completed
- `destroyRef = inject(DestroyRef); this.destroyRef.onDestroy(() => {subscription.unsubscribe()})`
- Operators to pipe on observable to another - like streams in java
- `interval(1000).pipe(map(url) => http.get(url), rxjs operators, ...).subscribe({})`
- can use multiple operators comma separated in pipe
- signals are built into angular, unlike observable
- effect in signal runs when underlying signal changes
- observables are values over time vs signals are values in a container
- obs for event management vs signals for app state
- obs have no initial value vs signals can have initial value
- `myObs$ = toObservable(mySignal)`
- `myObs$.subscribe({})`
- `mySignal = toSignal(myObs$)`

### Creating observable from scratch

```ts
const customInterval$ = new Observable((subscriber) => {
  let count = 0;
  const interval = setInterval(() => {
    if (count > 3) {
      clearInterval(interval);
      subscriber.complete({ message: "completed" });
    }

    subscriber.next({ message: "completed" });
    ++count;

    if (someError) subscriber.error({ error });
  }, 1000);
});

customInterval$.subscribe({ next, error, complete });
```

## Section 9

### Services

- Share logic and data across application
- Don't add `svc = new Service()`
- Rather just use in constructor `constructor(private svc: Service)` or use inject `svc = inject(Service)`

### Dependency Injection

- Let framework create and manage instances
- Injectors -> PlatformEnvironmentInjector, ApplicationRootEnvironmentInjector, ModuleInjector, ElementInjector, NullInjector (for errors)
- With AREInjector:
  - use providers: [] in bootstrapApplication
  - doesn't not provide treeshaking (optimization)
  - includes the service always
- With ElementInjector:
  - Only components and directives reach out to element injector
  - services don't
  - Closely tied to DOM Element
  - providers: [] in .component.ts file
  - component and all children have access
  - if multiple providers provided in component and children, each receive a new instance and not the same
  - each component also receives a new instance when used two times. eg: <app/> <app/> 2 instances => 2 instances of services, 1 for each
- Injecting services into services
  - can't use with ElementInjector
- CustomInjection
  - under the hood, this is how services are being injected
  - a new injection token is created and pass to providers array with token and class
  - to use the token elsewhere, inject it and use it
  - first register it in booststrapAppliation:
  - `myInjectionToken = new InjectionToken<Svc>('some-name')`
  - `providers: [{provider: myInjectionToken, useClass: Svc}]`
  - To use in component: `constructor(@Inject(myInjectionToken) private svc: Svc)`
  - To use as signal: `svc: inject(myInjectionToken)`
- Injecting Non-class values
  - eg: `tasks = [{value: "open"}, {value: "close"}]`
  - to provide in component => `providers: [{provide: customToken, useValue: tasks}]` and `customToken = new InjectionToken<MyType>('name')`\
  - `useFactory: () => {}` to generate dynamic values
- Modules & DI
  - `providers: []` the same way
  - if multiple modules use the same value, then all are merged via the Module Injector

## Section 12

### Connecting to backend

- `httpClient = inject(HttpClient)` and `provider: [provideHttpClient()]` -> provide app wide
- while injecting, provider is required to let angular know what and how to inject
- `this.httpClient.get('http://localhost:3000/places', {}).subscribe()` // if subscribe is not called, the api call is not triggered because obs trigger only when subscribe is called
- configure http objects -> {observe: "response"} sends full response metadata + data instead of just data
- {observe: "events"} -> trigger for multiple event in the requests response lifecyle eg: 2+ with request + response

```ts
const subscr = this.httpClient.get<>("http://localhost:3000/places").pipe(map(res) => (res.places), catchError((err) => throw new Error("something went wrong"))).subscribe({next, error, complete})
```

- to send data: `this.httpClient.post<>().subscribe()`
- moving http logic into services is better
- `pipe(tap({next, error, complete}))` use the data without subscribing
- Optimistic Updates
  - add to local data first and then send a request.
  - if already exists, skip sending
  - if error occured, restore to old state

### HTTP Interceptors

- Intercept a request or response

````ts
function LogInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn) {
  console.log(request);
  // request interceptor
  const req = request.clone({
    headers: request.headers.set('', '')
  });

  // response interceptor
  return next(req).pipe(tap({}));
}```
- in `providers: [provideHttpClient(withInterceptors[LogInterceptor])]`
````

## Section 13

### Template driven forms vs Reactive Forms

- TDF: Setting up forms via component templates (easy to start, complex to proceed)
- RF: More code via .ts, but handles complex logic

### TDF

- `imports: [FormsModule]`
- `<input ngModel name="" />` -> no 2 way binding but makes angular aware of the input element. if 2 way required `[(ngModel)]="var"`
- `name` attribute is a must when using ngModel
- `<form #form="ngForm"></form>` form is angular component not html element when using formsmodule. => form is now an ngForm in TDF
- all setup is done in template => TDF
- to access in .ts => use viewChild
- or pass in onSubmit => `<form #form="ngForm" (ngSubmit)="onSubmit(form)"></form>`
- `pristine` -> whether has some value in it
- `form.value` -> contains kv pair of form inputs
- `form.controls` -> contains each control
- validators when used with ngModel, angular takes control over HTML DOM. eg: required with ngModel -> angular checks
- `form.errors` contains the error for each field whether or not that field has an error
- `form.status` can be VALID or INVALID if the overal form is valid / invalid
- for user feedback => `form.form.controls['email'].touched && form.form.invalid` => error
- or have a template variable in each control <input #email="ngModel" /> and use `email.touched`
- debounceTime(500) and debounces that is waits for 500ms without receiving any event then runs.
- setValue, valueChanges
