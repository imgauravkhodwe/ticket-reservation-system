# ticket-reservation-system
Use case:

Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.
For example, see the seating arrangement below.
 
                                               ----------[[  STAGE  ]]----------
                                               ---------------------------------
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
                                               sssssssssssssssssssssssssssssssss
 

Your homework assignment is to design and write a Ticket Service that provides the following functions,
•	Find the number of seats available within the venue [Note: available seats are seats that are neither held nor reserved.]
•	Find and hold the best available seats on behalf of a customer [Note: each ticket hold should expire within a set number of seconds.]
•	Reserve and commit a specific group of held seats for a customer
 
Requirements:
```
•	Use a programming language that you are comfortable with. We work in Java, but we are more interested in understanding how you think than in language specifics.
•	The solution and tests should build.
•	A README file should be included in your submission that documents your assumptions and includes instructions for building the solution and executing the tests.
```
Assumption:
```
•	The theather/venue in for 10 rows and 50 coloum making total seat capacity of 500.
•	Data will be stored localy.
•	The seats can be placed on hold for 5 minutes after which they will be free/available
•	The seats will be reserved on first come first serve basis, the best seating order is starting close to the stage starting left to right.
•	An attempt will be made to keep all the users in one booking together in one row but if not possible they will be allocated seating starting close to the stage starting left to right which may put some user in a group in row behind.
•	4 APIs will be availble for the consumer a) To get total number of seats in the venue b) To get total number of FREE seat in the venue c) To reserve seats for 5 min. d) To book the seats the user had reserved
•	The will be three status for the seats a) HOLD b) FREE and c) BOOKED
```
Dependency used:
```
•	Lombok
•	Swagger
•	Springboot
•	maven
```
To run the project:
```
•	git clone https://github.com/imgauravkhodwe/ticket-reservation-system.git
•	cd in to the project folder
•	run mvn clean package spring-boot:run
•	Swagger can be reached at: http://localhost:8080/swagger-ui.html ( I recommend hit the APIs via postman, as swagger may sometime return inaccurate status code )
```