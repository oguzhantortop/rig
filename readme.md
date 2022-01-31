To run this program please first mvn install than execute commands below:

* docker build . -t rig:latest

* docker run  -p 8080:8080/tcp rig 

After running the program you can find the postman collection in Postman folder.

While running Postman requests please first start with Create Customer request and 
with the given username password execute Authenticate request to get bearer token. 
After successful username(email of user) and password you can run any other request.
Don't worry about setting token for other requests Postman will automatically do it for you.
