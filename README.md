Back-end service with authentication and authorization to view and edit payroll data of employees  

Available features without authentication:
1) Registration (POST /api/auth/register),sends JSON, gets JWT valid 30 min.
   The first user gets the administrator role, the other user roles
2) Authentication (POST /api/auth/authenticate),sends JSON,  gets JWT valid 30 min.

There are three types of users:
1) Administrator
2) Accountingtech(ACCOUNTANT)
3) User
4) Auditor

Available features with authentication for all users:
1) Change password (POST /api/auth/changepass), sends JSON with new password

Available features with authentication for administrator:
1) Changing the roles of other users (PUT /api/admin/user/role)
2) Getting a list of users (GET /api/admin/user/)
3) Deleting all users, including the administrator  (DELETE /api/admin/user/)
4) Deleting a single user (DELETE /api/admin/user/{userEmail})

Available features with authentication for Accountingtech:
1) Adding salary data (POST /api/acct/payments), sends JSON with list of salary data of employees
2) Change the salary of a specific user (PUT /api/acct/payments), sends JSON with employee data

Available features with authentication for User:
1) Getting the salary data (GET /api/empl/payment), the query can be with the parameter (period), then the data about salarie for this period will be returned, when you make a query without the parameter, the data about all salaries will be returned

Available features with authentication for Auditor:
1) Getting a list of logs (GET /api/security/events/)

An HTTPS connection with its own certificate is used:
-genkeypair -alias accountant_service -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650
Password 952738

The data is stored in a PostgreSQL database.
Password is stored as Hash

To copy a project, you must:
1) Go to the directory where you will install the project
2) Type in git bash: git clone https://github.com/UladzislauBlok/AccountService
3) Open and run the project in an IDE (Eclipse or Intellij IDEA), they will automatically download all dependencies and build the project

p.s. You must have PostgreSQL installed
