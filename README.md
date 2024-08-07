# Human Resource Managements - Rest Client
The Human Resources Management project aims to provide a basic system for managing employees, departments, projects, positions, and tasks in a small company.

The application is divided into two subprojects, namely:

Rest Client -> responsible for the registration of new users and the front-end.
Rest Server -> containing the database and the business logic of the application.

To register a new user, the employee's personal identification number from the database is required. The first registered user has administrator rights. For example, use the following social security number: 1208117890.

Additionally, an init file has been created for loading with roles.
For the purposes of this demo, the project runs as a Rest Client, communicating via REST requests with [ https://github.com/mr-pachev/hrm-data.git ], which runs as a Rest Server.
