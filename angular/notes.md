# Angular Notes

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

```ts
onSubmit(form: NgForm) {

}

constructor() {
  afterNextRender(() => {
    this.form.valueChanges...
    this.form.setValue()...
  })
}
```

```html
<form #form="ngForm" (ngSubmit)="onSubmit(form)">
  <input #password="ngModel" ngModel name="password" />
  <input #email="ngModel" ngModel name="email" />
</form>
```

### Reactive Forms

- handle form from .ts rather from template
- imports: [ReactiveFormsModule]

```ts
const myValidator = (control: AbstractControl) => {
  if (control.value.includes("?")) return null;
  return { doesNotHave: true };
};

const myAsyncValidator = (control: AbstractControl) => {
  if (control.value !== "") {
    return of(null); // null as obs
  }

  return of({ notUnique: true });
};

const form = new FormGroup({
  email: new FormControl("initial", {
    validators: [Validators.required, myValidator],
    asyncValidators: [myAsyncValidator], // must be an array of obs
    nonNullable: false,
    updateOn: "",
  }),
  password: new FormControl("initial"),
});
```

```html
<form [formGroup]="form" (ngSubmit)="onSubmit()">
  <input formControlName="password" />
  <input [formControl]="form.controls.email" />
</form>
```

- patchValue to partial update an overall form
- nested form groups, to access <div [formGroupName]="passwords"></div> where password = new FormGroup({password: new FormControl(), confirmPassword:new FormControl()})
- array of form elements: source: new FormArray([new FormConrtol(), new FormControl()])
- create a FormArray with as many FormControl as required. and to use it => formArrayName="source" and the element needs formControlName"0" index of the form array
- FormGroup can also take validators just like FormControl

## Section 14

### Routing

- Route to use different urls for serving different pages even though it is a SPA
- It should feel like an application with multiple pages
- Angular has CSR -> Client Side Routing
- To have routing => in providers in root -> [provideRouter([{path: "", component: }])]
- use <router-outlet></router-outlet> to tell where to render the route output
- Needs RouterOutlet in imports
- <a [routerLink]=["/tasks", "1"] routerLinkActive="selected" /> needs RouterLink imports
- to use dynamic routes -> path: ":userid" -> with : makes it dynamic
- get params via @Input or input() providerRouter([], withComponentInputBinding()) // to access params via input binding
- alternate way as observable
- activatedRoute = inject(ActivatedRoute) contains a list of observables
- activatedRoute.paramMap.subscribe({next: paramMap => paramMap.get('id')})
- nested routes => {path: "users", component: "", children: [{path: "id", component}]}
- this nested route also needs in router-outlet
- relative links don't start with `/` they are appended to current route link
- to get parent params -> provideRouter([], withComponentInputBinding(), withRouterConfig({
  paramsInheritanceStrategy: "always"
  }))
- to route programmatically -> router = inject(Router), router.navigate([], {replaceUrl: true // avoid going back to this})
- not found -> path: "\*\*"
- to redirect -> path: "", redirectTo: "", pathMatch: "prefix" (not full pathMatch) alternative is full
- full checks for full path match, prefix checks for only the path.
- [queryParams]="{order : 'asc'}"
- can access static data via data: {} in routes
- dynamic data via resolve: {userName: (activatedRoute: ActivatedRouteSnapshot, router: RouterStateSnapshot) => {svc = inject(UserService); userName = svc.users.find(u => u.id === activatedRoute.paramMap.get('userId')); return userName}}
- userName = input()
- resolve executes by default is route param changes and not query param changes
- to rerun -> runGuardsAndResolvers: 'always' or 'paramsOrQueryParamsChange'
- to update title / metadata in angular routes -> title: "" property in routes or dynamically -> title: (activatedRoute: ActivatedRouteSnapshot, router: RouterSnapshot) => {}
- route guards -> canActivate, canMatch, etc. -> returns true / false based on logic
- can also redirect based on new RedirectCommand(router.parseUrl('/'));
- canDeactivate before leaving the page -> throw some alert
- to reload same page-> router.navigate(["./"], {relativeTo: this.activatedRoute, onSameUrlNavigation: 'reload', queryParamsHandling: 'preserve'})
