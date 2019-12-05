The project was developed using RabbitMQ as a message broker (To pass the message between oven and product service). To run the project, the below instruction has to be followed-
1.	Download and install Erlang which is a prerequisite of RabbitMQ. Link: https://www.erlang.org/downloads
2.	Download and install RabbitMQ. Link: https://www.rabbitmq.com/download.htmlhttps://www.rabbitmq.com/download.html
3.	One RabbitMQ feature which is extremely useful (but which isn’t enabled by default) is the web-based management interface. With this, you can see the exchanges and queues that are set up by MassTransit in RabbitMQ. To enable this, find the “RabbitMQ Command Prompt (sbin dir)” item that the RabbitMQ installer added to your Start menu and launch it. From the command line, run the following command:
rabbitmq-plugins enable rabbitmq_management
4.	It will confirm that the plugin and its dependencies have been enabled and instruct you to restart RabbitMQ. When installed on Windows, RabbitMQ runs as a Windows service. You can use the Services MMC snap-in to restart it or just run the following command:
net service stop RabbitMQ
net service start RabbitMQ
5.	Now you can go to http://localhost:15672/ to open the management console. Default credentials to login are guest/guest
6.	The project is developed using Maven build tools. Open the project by pointing the POM.xml file with any IDE (preferably eclipse)
7.	There is a class called OvenService. Running the main method of this class N times means there are N number of ovens up and running to burn product.
8.	There is a class called ProductService. Running the main method of this class 1 time will create arbitrary number of product and it will be sent to the Scheduler (RabbitMQ) to get service from ovens.
