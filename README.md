# component_management

A Spring Boot-based component management system. This guide helps you install and run the project on **Ubuntu OS**.

---

## 🛠️ How to Install and Run This Project

### 1. Install Java and Clone the Repository

```bash
# Update package lists
sudo apt update

# Install required packages
sudo apt install git openjdk-17-jdk maven

# Clone the project repository
git clone https://github.com/qijing223/component_management
cd component_management
```

---

### 2. Set Up PostgreSQL Database

This project uses **PostgreSQL** as the database. Follow these steps to install and configure it.

#### 📥 Install PostgreSQL

```bash
sudo apt install postgresql postgresql-contrib
```

#### 👤 Create a Database User and Database

```bash
# Switch to the postgres user
sudo -i -u postgres

# Create a new PostgreSQL user (you will be prompted to enter username and password)
createuser --interactive --pwprompt

# Create a new database owned by the new user (replace 'component_db' and 'myuser' accordingly)
createdb component_db --owner=myuser

# Exit postgres user
exit
```

#### 📝 Run Initialization Script (Optional)

If you have a SQL script (e.g., `init.sql`) to set up the schema:

```bash
# Run the SQL script (adjust the path and credentials as needed)
psql -U myuser -d component_db -f ./init.sql
```

> Tip: If your database requires a password, you can either export `PGPASSWORD` or configure a `.pgpass` file for convenience.

---

### 3. Configure Spring Boot Application

Edit the database settings in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    hikari:
      max-lifetime: 1800000
      idle-timeout: 600000
    url: ${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost/{new_database_name}}
    username: ${SPRING_DATASOURCE_USERNAME:{user_name}}
    password: ${SPRING_DATASOURCE_PASSWORD:{user_password}}
    driver-class-name: org.postgresql.Driver

mybatis:
  configuration:
    map-underscore-to-camel-case: true
    default-enum-type-handler: org.apache.ibatis.type.EnumTypeHandler

lot:
  jwt:
    secret-key: LoTBackEndSecurityKeyUsedForAuthorization
    ttl: 7200000
    token-name: Authorization
```

Replace:

* `{new_database_name}` with your created database name (e.g., `component_db`)
* `{user_name}` and `{user_password}` with the credentials you just created

> ⚠️ If your database is hosted on a different server, update `localhost` with the database host's IP address.

---

### 4. Build and Run the Spring Boot Server

```bash
# Build the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
```

> ✅ Make sure **Java 17** or higher is properly installed before running the application.

---
