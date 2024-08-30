This project consists in a FullStack web application that allows the user to manage a simple inventory and sales associated to items in said inventory. 

For data persistence, a MySQL database is used. The backend was created using Spring Boot, and the frontend using Angular with Node.

The application is configured to be deployed via Docker, thus the only prerequisites are having Docker installed on the machine, plus a MySQL server running on localhost:3306, having previously executed "createDB.sql" script.
It is necessary to have both services started and ready in order to build and/or run the Docker images. The MySQL version used is 8.0.35
