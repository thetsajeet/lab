# Data Models and Query Languages

- Data models are most important part of software engineering as it thinks about how to approach the problem we are solving.
- Each data model is a wrapper on top of a lower-level data model. Eg: APIs built on top of JSON documents.

## Relational vs Document Model

- SQL-based relational data model where data is organized into relations (tables) and each relation is an unordered collection of tuples (rows).
- NoSQL was introduced:
  - Greater scalability
  - Open source
  - Specialized query operations
  - Frustration with restrictiveness of relational schemas

### Object Relational Mismatch

- Programming languages model Objects and SQL requires tables and rows.
- Due to this mismtach, an ORM (Object Relational Mapping) Framework is required to map objects to sql data models.
- The mismatch is called an impedence mismatch

### 1 - Many relationship

- Eg: one user can have multiple education degrees
- in SQL:
  - normalized tables. user table and education table and reference by foreign keys
  - store multi-valued data in single row (eg: education -> 1,2,3)
  - encode as a json and store in the column (can't index content inside json)
- JSON models are well suited for document databases (mongodb, couchdb, etc)
- 1 - Many can represent a tree structure

### Many - 1 relationship

- Eg: many users might come from the same area. like India
- So storing it as a plain text is not great, as there are duplicates (hence normalization)
- Instead store the area in a separate table and use the id => many advantages
- many-1 doesn't fit well in document databases as there are weak joins.
- best in sql databases

### Many - Many relationships

- If many users link to many other orgs, entities, etc. => many-many doesn't fit well in document databases again.

### Different models in history

- Initially there was hierarchial model that represented a JSON structure
- didn't work well for many-many and joins
- so 2 models were born: Network and Relational model

#### Network Model

- Network model followed the approach where each record could have multiple parents
- Links between multiple records were considered pointers. To discover a relationship, travel across all links and many such paths (access path) exist.
- To find records, you have to travel multiple paths from base record.
- Very difficult and time consuming process

#### Relational Model

- Put data in tables and rows.
- Comes with a query optimizer that decides which is the best way to query data.
- User can enter any query and query optimizer will optimize and find the best access path to get data
- Can also modify query optimizer by creating / modifying indices

### Relational vs Document Model

- Document:
  - better flexibility
  - better performance due to locality
  - closer to application code
- Relational:
  -
