# Introduction to DBMS

- A **DBMS - Database Management Systems** is a collection of interrelated data and a set of programs to manage those data. 
- Collection of data: **Database**
- Store and retrieve db information both conveniently and efficiently.
- There are 2 modes in which DBMS are used:
	- **OLTP**: 
		- OnLine Transaction Processing
		- When large number of users use DB to perform small amounts of reads and writes
	- OLAP:
		- OnLine Analytical Processing
		- When business users / analysts use DB to draw conclusions from a very large set of data.


## Why DBMS?

- Alternative is to store in files provided by OS and perform reads / writes.
- **File-based processing system**
- Issues:
	- Data redundancy
	- Data inconsistency
	- Difficulty to access derivations of data involving 2 or more files
	- Data isolation
		- files could be in different formats / different application programs to retrieve it
	- Integrity constraints:
		- satisfy certain consistency constraints such as value > 0, must be string, etc.
	- Atomicity problems:
		- some files are updated / some not - cause issue. should not exist
		- atomic: entirely happen or not happen at all
	- Concurrent-access anomalies:
		- if one file is used by 2 users at different steps, both must be consistent before and after update.
	- Security:
		- Difficult to implement RBAC
## Data Models

- A data model is a collection of conceptual tools to describe data, semantics, relationship, consistency constraints.
- 4 broad categories:
	- relational data model
		- collection of tables to represent data and relationship among data.
		- each table has multiple columns called attributes
		- each row is called a tuple
		- each table is also called as relation
		- each record in a table corresponds to fixed-record type. even some attributes don't have value for a row, we have some space allocated for it in the physical storage.
	- ER model:
		- collection of basic objects (entities) and relationship among these objects.
	- Semi-structured data:
		- permits individual data can have varied attributes. eg: student has grade and not salary, while instructor has salary and not grade attribute.
		- Eg: XML, JSON data types
	- Object-based data model:
		- Standards exist to store objects of programming languages into relational tables.
		- Procedures to be stored in system and be executed by the system.