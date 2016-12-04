
 
# APPLICATION AUTHORIZATION FRAMEWORK
			       
## OVERVIEW
  
The purpose of AAF (Application Authorization Framework) is to organize software authorizations so that applications, tools and services can match the access needed to perform job functions.  This is a critical function for Cloud environments, as Services need to be able to be installed and running in a very short time, and should not be encumbered with local configurations of Users, Permissions and Passwords.

To be effective during a computer transaction, Security must not only be secure, but very fast. Given that each transaction must be checked and validated for Authorization and Authentication, it is critical that all elements on this path perform optimally.

AAF contains some elements of Role Based Authorization, but includes Attribute Based Authorization elements as well.  
 
 

## BUILD  
 
AAF can be cloned and repository and builb using Maven 
In the repository 

Go to Authz in the root

	Run - mvn -N install
Go to Authz-Client in the Authz directory in root

	Run - mvn -N install
Go to inno directory in the root

	Run - mvn clean install
Go to Cadi in the root 

	Run - mvn clean install
Go to Authz directory in the root 

	mvn - clean install
Project Build will be Successful




## RUN 

AAF is a Unix based service 

Pre-requisites to run the service

Cassandra Version 2.1.x

Java JDK 1.8

Install cassandra and load create default namespace 'authz' and load needed table into the database

Sample init.cql is provided in the authz-cass module of Authz directory.

Go to authz/authz-service module and run the service using start.sh (Modify the script as required. Default is provided in the code)
 

 
## CONFIGURATION 

Recommended 

Environment - Unix based

Java - 1.8

Maven - 3.2.5 

Cassandra - 2.1.x

 
 
