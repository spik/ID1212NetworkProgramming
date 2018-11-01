# ID1212NetworkProgramming

These assignments where done for the course "Network Programming" (ID1212) at KTH (Royal Institute of Technology) between November and January 2017. 

## Homework 1, Blocking Sockets

This assignment was to implement the hangman game as a client-server distributed multi-threaded application using blocking TCP sockets.

## Homework 2, Non-blocking Sockets

Same as assignment 1, only this time the application uses non-blocking sockets instead of blocking sockets. 

## Homework 3, RMI and Databases

This assignment was to implement a client-server distributed application that uses remote method invocation for inter-process communication. The application 
allows storing, retrieving and removing files to/from a file catalog. The client is controlled by a user and provides a user interface. The server can perform the following actions:

- A user can register at the catalog, unregister, login and logout. To be allowed to upload/download files, a user must be registered and logged in.
- A user specifies username and password when registering at the file catalog server. On registration, the server verifies that the username is unique. 
If it is not, the user is asked to provide another username.
- When logging in to the server, a user provides username and password. The server verifies the specified username and password.
- A user can upload a local file to the catalog and download files from the catalog to the local file system. 
- A private file can be retrieved, deleted or updated only by its owner. A public file can be accessed by any user registered at the file catalog. 
The write and read permissions for a public file indicates whether the file is read-only for other users than the owner or if it can be modified.
- Users can inspect what files are available in the file catalog, i.e. list the files in the catalog and their attributes. If a file is marked as private, it can be listed only by its owner.
- A user can request to be notified when other users access one of its public files. The user tells the server for which files it wants to be notified. When one of those
public files has been read or updated by another user, the server tells the owner who performed the action, and what action was taken.

The server uses a database to keep records on each user (user name and password) and on each file in the catalog (name, size, owner, public/private access permission, write/read permissions).

## Homework 4, Web-Based Applications And Application Servers

This assignmnet was to implement a three-tier web-based application for online currency conversion. 

## Homework 5, Distributed Applications Using Android

The task for this assignment was to implement the hangman game from homework 1 for android applications using Android SDK. 

## Project

For the project I developed an online booking system using RESTful web services. The user can book a hotel room, view, change and delete the booking, view a particular room and view all
available hotel rooms. The user can also get all available rooms that cost less than a price limit provided by the user, and get all rooms of a specific type (for example single, double, 
honeymoon suite and so on). The application consists of a client that makes HTTP requests to the server (GET, POST, DELETE and PUT), and the server handles the requests. The server is run 
on a Payara Server. The client is run as an ordinary Java application. 
