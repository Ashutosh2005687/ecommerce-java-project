# Online E-Commerce Website (Review-1)

## Overview
Simple desktop e-commerce admin console built with Java Swing, JDBC and MySQL. This deliverable contains the project skeleton, model classes, DAO classes, a basic Swing UI and SQL schema.

## Setup
1. Install Java 11+ and MySQL server.
2. Create database and tables: run `sql/ecommerce_schema.sql` using MySQL client or Workbench.
3. Update DB credentials in `src/com/ecommerce/dao/DBConnection.java` (URL, USER, PASS).
4. Compile and run: from project root:
   ```bash
   javac -d out $(find src -name "*.java")
   java -cp out com.ecommerce.ui.MainFrame
   ```

## Project Structure
- `src/com/ecommerce` : Java source (model, dao, ui, util)
- `sql/ecommerce_schema.sql` : SQL schema & sample data

## Notes
- Password hashing utility included for demo in `com.ecommerce.util.Utils` (SHA-256). Use bcrypt/argon2 in real apps.
- This is a Review-1 skeleton. Add more UI and business logic for full functionality.
