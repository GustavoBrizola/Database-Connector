# Database Connector

Simple Java project that demonstrates connecting to a relational database via JDBC. This repository contains a minimal example showing how to open a JDBC connection modify it and run a query.

The main goal to achieve from this ongoing project is have a quick, and well structured library when connections with databases is nedeed in any part on other project, so it won't be necessary build up connection all over from scratch.

This project serves as a reference implementation for:
- Establish a secure database connection
- Execute modular queries prompts safely
- Handling database transactions effectively

## Overview
This is a Java database connection sample project containing multiple modules that are both production-ready and in active development. The project demonstrates best practices for establishing and managing database connections.

### Modules
#### Available Modules
- **Settings**: Core database connection handling and pooling
- **Operations**: Creates queries, executes and prints on terminal.
#### In Development
- **Export**: Database schema versioning and import utilities
- **Import**: Database schema versioning and export utilities


## Quick Start
To initialize as an object, simply paste the line of code below.

``` JAVA
import database.source.DatabaseConnector;

DatabaseConnector database = new DatabaseConnector(type, protocol, host, database, user, password);
```
dasd
protocols

| Parameter     | Description   | Data Type |
|---            |---            | ---
| **Type**      | This variable determines witch database engine system the URL protocol will use, along with the host, port and database parameter. For now, only MySQL system is avaiable. Future plans include connect with Postgree and Oracle Database systems.    | Int
| **Host**      | The server IP address domain.              | String
| **Port**      | Network port.  | String
| **Database**  | Database name located for access.                | String
| **User**      | User name credential.              | String
| **Password**  | Authentication password.              | String


## Example usage

### SQL Settings
Settings module can be called to modify aspect of the connection. Here some examples.
``` JAVA
// Checks if there's connection, return boolean
database.Settings.CheckConnection();

// Disconnects from database
database.Settings.Disconnect();

// Change user credentials. Automatically disconnect to apply changes and reconnect
database.Settings.ChangeCredentials(user, password);

// Makes connection
database.Settings.Connect();
```

### SQL Operations
The operations allow to execute SQL queries. You can run the query and print on terminal separately using **RunQuery()** and **Terminal()**.

> [!IMPORTANT]
> The project as it is right now, does not prevent against SQL injection in any shape or form. Future development must include PreparedStatement preventions.

``` JAVA
// Select table
database.Operations
    .SELECT("*")
    .FROM("table")
    .RunQuery()
    .Terminal();

// Add rows
// Only builds the query
database.operation
    .INSERT("INTO Table(Columns)")
    .VALUES("(data), (data)");

// Delete database
// Build, runs the query only
database.operation
    .DROP("DATABASE database")
    .RunQuery();

// Selects table with rules
database.operation
    .SELECT("*")
    .FROM("table")
    .WHERE("Condition")
    .RunQuery()
    .Terminal();
```