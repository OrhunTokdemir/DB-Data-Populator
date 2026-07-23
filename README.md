# DB Data Populator

A robust Java tool designed to populate PostgreSQL and Oracle databases with comprehensive random data for testing and development purposes. It leverages [Datafaker](https://www.datafaker.net/) to generate realistic data across a wide range of database types.

## Features

- **Multi-Database Support**: Built-in managers for PostgreSQL and Oracle.
- **Comprehensive Data Types**: Supports standard types (VARCHAR, INTEGER, BOOLEAN) as well as advanced PostgreSQL types (JSONB, UUID, CIDR, Geometric types, Arrays, and Enums).
- **Realistic Data Generation**: Uses Datafaker for names, addresses, and other formatted strings.
- **Docker Integration**: Includes a `docker-compose.yml` for quick PostgreSQL setup.
- **Easy Configuration**: Simple JDBC-based connection management.

## Prerequisites

- **Java 17** or higher
- **Gradle**
- **Docker & Docker Compose** (optional, for local PostgreSQL instance)

## Quick Start

### 1. Set up the Database
If you don't have a PostgreSQL instance running, you can start one using the provided Docker Compose configuration:

```bash
docker-compose up -d
```
This will start a PostgreSQL 17 instance on port `8004` (as defined in `docker-compose.yml`).

### 2. Configure Connection
The connection details are currently managed in `src/main/java/org/orhuntokdemir/app/Main.java`. You can modify the URL, user, and password:

```java
String url = "jdbc:postgresql://localhost:8004/postgres";
String user = "postgres";
String password = "postgres";
```

### 3. Run the Application
You can run the application using Gradle:

```bash
./gradlew run
```

Or, if your IDE supports it, run the `Main.java` class directly.

## Usage

The application uses an `InsertionManager` to coordinate the data generation process. You can specify the number of rows (records) to be inserted in the `manager.run()` method.

Here is a basic example of how it is used:

```java
// how many records to enter
int recordCount = 200;

try (DbManager dbManager = new PostgreManager(url, user, password)) {
    PostgreDataInserter inserter = new PostgreDataInserter(dbManager);
    InsertionManager manager = new InsertionManager(inserter);

    // Pass the recordCount variable to the run method
    manager.run(recordCount, false);
} catch (SQLException e) {
    e.printStackTrace();
}
```

- **recordCount**: The variable defining the total number of rows to generate and insert (e.g., 200).
- **dropFirst**: Set to `true` if you want to drop the existing table before insertion.

### Table Schema
The `PostgreDataInserter` creates a table named `test_data` with the following columns (and more):
- `id` (SERIAL)
- `col_name` (VARCHAR)
- `tc_kimlik_no` (CHAR(11) with validation)
- `col_timestamp` (TIMESTAMP)
- `col_jsonb` (JSONB)
- `col_point` (POINT)
- `col_array` (INTEGER[])

## Configuration (.env)

A `.env.example` file is provided. You can create a `.env` file to customize the PostgreSQL password and port used by Docker Compose:

```env
POSTGRES_PASSWORD=your_password
POSTGREDEV_PORT=8004
```

## Development

### Adding New Data Types
To add support for more data types or custom logic, modify `RandomDataGenerator.java` to include new generation methods and update the corresponding `DataInserter` implementation.

### Running Tests
To run the test suite:

```bash
./gradlew test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
