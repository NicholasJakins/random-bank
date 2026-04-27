## Functional Requirements

- Customers must be able to register by providing basic details such as name, address, username,
and date of birth.
- Each customer must choose a unique username. If the selected username already exists, an
appropriate error message should be returned.
- After successful registration, a unique IBAN account number must be automatically generated
according to the Dutch (NL) IBAN format.
- A default password should be generated for the customer after registration.
- Customers must be able to log in using their chosen username and the generated default
password (password encryption is not required).
- Once logged in, customers must be able to view basic account information, such as the account
balance and account type.
- Registration and account creation should be allowed only for customers from the Netherlands
and Belgium.
- The system should make it easy to add new countries to the list of allowed countries in the
future.
- Only customers 18 years of age or older are permitted to register and create an account.

## Approach details

- [ ] POST /login needs to generate a session token
- [ ] POST /register 
- [ ] GET /overview needs to be tied to a session, spring security backed by postges, with username check
- [ ] Registration and account creation should include a country code, can be in the payload. List of supported country codes in application yaml
- [ ] Schemas
  - _sessions_ table for managing logins and sessions
  - _customer_ holds name and surname
  - _address_ holds foreign key to customer
  - _credentials_ holds password and username (primary key)

## Technical requirements and tools

- OpenAPI with code generation for models and endpoints
- Logging framework like SLF4J
- Lombok
- PostgreSQL
- Docker-compose for building the application
- JUnit

### Nice to have

- Masking of sensitive date
- Log out functionality
- Spring security for login
- Test containers
- Support for updating account type, balance
- Hashing of password in the database