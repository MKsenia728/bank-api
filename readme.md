# Bank Project [Backend]

There is a prototype of the BackEnd Bank's Core Services data.

Data consist of clients, accounts, products, accounts, transactions and managers
For currency, as well as for rounding, use the BigDecimal class.

---

## Database queries implemented

### Accounts

1. GET: Find account by id
2. GET: Find account by name
3. GET: Find all names of accounts
4. GET: Find all accounts by status
5. POST: Create new account
6. PUT: Upgreate (change status to BLOCKED) for all accounts with given status and product id

### Clients

1. GET: Find all clients with balance more than given value and given currency

### Managers

1. GET: Find manager by id
2. GET: Find all managers with clients
3. POST: Create new manager

---

## Used libraries, frameworks, technologies

### Spring-boot

### Hibernate

### Liquibase

### Jacoco

### Mapstruct

### Swagger

### Mockito

---

## Database structure

### Table clients ( Bank's Clients table )

| Column name | Type        | Description                                   |
| ----------- | ----------- | --------------------------------------------- |
| id          | binary(16)  | id key of row - unique, not null, primary key |
| manager_id  | int         | manager id (table managers)                   |
| status      | varchar(10) | client status selection from a list of values |
| tax_code    | varchar(20) | client's TAX code                             |
| first_name  | varchar(50) | client's name                                 |
| last_name   | varchar(50) | client's surname                              |
| email       | varchar(60) | client's e-mail                               |
| address     | varchar(80) | client's address                              |
| phone       | varchar(20) | client's phone                                |
| created_at  | timestamp   | timestamp of row creation                     |
| updated_at  | timestamp   | timestamp of last update                      |

### Table accounts (Bank's accounts table)

| Column name   | Type          | Description                                   |
| ------------- | ------------- | --------------------------------------------- |
| id            | binary(16)    | id key of row - unique, not null, primary key |
| client_id     | binary(16)    | client's id (table clients)                   |
| account_name  | varchar(100)  | a name of account, IBAN                       |
| type          | varchar(10)   | account selection from a list of values       |
| status        | varchar(10)   | status selection from a list of values        |
| balance       | numeric(15,2) | balance of the account in currency.           |
| currency_code | varchar(5)    | account currency code from a list of values   |
| created_at    | timestamp     | timestamp of row creation                     |
| updated_at    | timestamp     | timestamp of last update                      |

### Table products ( Sets of Bank's available Products)

| Column name   | Type         | Description                                                              |
| ------------- | ------------ | ------------------------------------------------------------------------ |
| id            | int          | id key of row - unique, not null, primary key                            |
| product_name  | varchar(70)  | product's name                                                           |
| status        | varchar(10)  | product's status from a list of values                                   |
| currency_code | varchar(5)   | currency of product from a list of valuers                               |
| interest_rate | numeric(6,4) | interest rate of product                                                 |
| limit_credit  | int          | limit of credit a product ( 0 - no limit, 0 < - limit which can be used) |
| created_at    | timestamp    | timestamp of row creation                                                |
| updated_at    | timestamp    | timestamp of last update                                                 |

### Table agreements (Bank's - Client's Agreement table)

| Column name   | Type          | Description                                   |
| ------------- | ------------- | --------------------------------------------- |
| id            | int           | id key of row - unique, not null, primary key |
| account_id    | binary(16)    | client's account (table accounts)             |
| product_id    | int           | product id (table products)                   |
| interest_rate | numeric(6,4)  | current interest rate of agreement            |
| status        | varchar(10)   | agreement's status from a list of values      |
| sum           | numeric(15,2) | amount of agreement                           |
| created_at    | timestamp     | timestamp of row creation                     |
| updated_at    | timestamp     | timestamp of last update                      |

### Table transactions (Bank's Transaction table)

| Column name       | Type          | Description                                   |
| ----------------- | ------------- | --------------------------------------------- |
| id                | binary(16)    | id key of row - unique, not null, primary key |
| debit_account_id  | binary(16)    | transaction's debit account                   |
| credit_account_id | binary(16)    | transaction's credit account                  |
| type              | varchar(10)   | transaction from a list of values             |
| amount            | numeric(15,2) | transaction amount in the account currency    |
| description       | varchar(255)  | description of transaction                    |
| created_at        | timestamp     | timestamp of row creation                     |

### Table managers (Bank's managers )

| Column name | Type         | Description                                   |
| ----------- | ------------ | --------------------------------------------- |
| id          | integer      | id key of row - unique, not null, primary key |
| first_name  | varchar(50)  | manager's name                                |
| last_name   | varchar(50)  | manager's surname                             |
| status      | varchar(10)  | manager's status from a list of values        |
| description | varchar(255) | description of transaction                    |
| created_at  | timestamp    | timestamp of row creation                     |

### Table products-managers ( Table for ManyToMany for tables Products and Managers)

| Column name | Type | Description                                   |
| ----------- | ---- | --------------------------------------------- |
| id          | int  | id key of row - unique, not null, primary key |
| manager_id  | int  | manager id (table managers)                   |
| product_id  | int  | product id (table products)                   |
