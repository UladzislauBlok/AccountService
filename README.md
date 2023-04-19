Back-end service with authentication and authorization to view and edit payroll data of employees  

Available features without authentication:
1) Registration (POST /api/auth/register),sends JSON, gets JWT active 30 min.
2) Authentication (POST /api/auth/authenticate),sends JSON,  gets JWT active 30 min.
3) Upload payrolls (POST /api/acct/payments), sends JSON with list of salary data of employees
4) Change the salary of a specific user (PUT /api/acct/payments), sends JSON with employee data

Available features with authentication:
1) Change password (POST /api/auth/changepass), sends JSON with new password

The data is stored in a PostgreSQL database.
Password is stored as Hash

To copy a project, you must:
1) Go to the directory where you will install the project
2) Type in git bash: git clone https://github.com/UladzislauBlok/AccountService
3) Open and run the project in an IDE (Eclipse or Intellij IDEA), they will automatically download all dependencies and build the project

p.s. You must have PostgreSQL installed

Stage 4/7. 
