# Todo_Server
by Leandro Hoenen and Kiril Buga

Our ToDo server implements all Minimum requirements

But additionally:
Optional features for more points, the server:
	Validates data on the server	(Server validates email address quality and the password length)
	Supports due-dates	
	Uses real tokens for user logins (random numbers and chars)
	The server can also remove disconnected clients
	Exception handling of wrong data
	
Optional feature: MVC client
	an MVC client that supports all API features
	Asks for IP address and port of server before login and registration
	It supports due-dates 
	All ToDo entries are displayed
	
	It displays all ToDo entries in a list
	It notifies the user if the password or the user name are wrong
	It also notifies the user  via an alert if the new password doesn't fulfil the requirements
	Login and Register button works only if the connection is correct
	Delete button works only if a ToDo is selected
	