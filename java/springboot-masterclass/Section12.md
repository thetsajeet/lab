# Section 12 - Managing multiple entities with JPA

- use annotations to map relationships among entities
- @OneToOne() for one to one relationships
- Can have unidirectional and bidirectional relationships on top of type of relationships
- in bidirectional:
  - no need to create foreign key columns on both tables
  - instead create a OneToOne() on the owner relationship
  - create only OneToOne(mappedBy="key") on the sub relationship. this will not create a column in the sub table but will be used since it's a bidirectional relationship.
- OneToMany is managed by OneToMany() which creates a new table that joins the two tables. If you want an owner in the relationship, set up mappedBy as before
- ManyToMany using JoinTable since 2 tables are joined. a new table is created
- CommandLineRunner xyz() is run when application context is init
- @JsonIgnore to avoid circular references
- order of saving items are important when relationships exist
- cascading:
  - when changing one must be automatically update the necessary changes in related entities without manual instruction
  - persist (save), merge, remove (delete), refresh, detach, all
- custom setters for specifying bidirectional relatoniships

## Fetch Types

- define how and when entities are loaded from the database in relation to parent entity
- types:
  - LAZY: loaded only when specified
  - EAGER: loaded when fetching parent entities
- Default fetch types:
  - 1-1 eager
  - 1-\* lazy
  - \*-\* lazy
  - \*-1 eager
- 1 on the right side => eager, \* on the right side => lazy
