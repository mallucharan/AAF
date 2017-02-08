
 
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


## AAF Docker Documentation ( Centos 7)

Make sure you have installed Docker & Docker Compose on your VM / Server (centos)

In case if you are using Ubuntu use the following link to install docker 
https://www.liquidweb.com/kb/how-to-install-docker-on-ubuntu-14-04-lts/

-----------------Docker install Instructions ( Centos 7)----------------------
     Update yum: Enter "yum update"
     Install Maven: Enter "yum install maven"
     Install Git: Enter "yum install git"
Create file  /etc/yum.repos.d/docker.repo with below contents in it - 
         [dockerrepo]
         name=Docker Repository 
         baseurl=https://yum.dockerproject.org/repo/main/centos/7/ 
         enabled=1 
         gpgcheck=1 
         gpgkey=https://yum.dockerproject.org/gpg
Install Docker Package: 
Enter "yum install -y http://yum.dockerproject.org/repo/main/centos/7/Packages/docker-engine-1.11.2-1.el7.centos.x86_64.rpm"
Enable the Service: Enter "systemctl enable docker.service"
Start the Docker Daemon: Enter "systemctl start docker"
-----------------Docker compose Instructions----------------------
Note: Cassandra Version 2.1.x is required & Docker compose version 1.8.0
 
*Skip the following block if you already installed docker-compose
To  install docker compose follow the commands
curl -L https://github.com/docker/compose/releases/download/1.8.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
 
to check version:  docker-compose –version
------------------------------------------------------------------------------------------------
 
Step 1: Download the zip folder docker-compose.zip from the attachment.  Or  clone it from github

Command: Git clone https://github.com/att/AAF
Path to docker- compose folder
1.cd  AAF/authz/authz-service/src/main/resources/docker-compose   ( if you are using git clone)
Or
Cd docker-compose   ( if using zip folder attached with this documentation)
Make sure folders & files are with porper read & write permissions
chmod –R 777 docker-compose

2.docker-compose up -d
 
at this point AAF & Cassandra Images will be downloaded and containers will start and service will be up.


To check running containers  
Docker ps –a

To check container logs
Docker logs container-name/ID

To access files inside the container
docker exec -it dockercompose_aaf_1 bash
AAF files located in the following folder    : opt
opt/app/aaf/authz-service/2.0.15/etc
opt/app/aaf/authz-service/2.0.15/lib

to find authz-service logs find them in the following folders:
logs
_LOG_DIR_


 
Step 2:  access the cassandra command line from bash
---------------------------------------------------
 
docker exec -it dockercompose_cassandra_1 bash
 
1.cqlsh     
2.use authz;   ( to use keyspace)
3.select * from ns;     ( to check name spaces ,perm,roles etc)
 
To come out from bash & container
quit
exit

Step 3:  AAF Command line to  create & grant permissions to create topics

1.docker exec -it dockercompose_aaf_1 bash
2.cd opt/app/aaf/authz-service/2.0.15/
3. sh runaafcli.sh –c
Aaf_id :   dgl@openecomp.org
Pass:        ecomp_admin

At this point you get access to AAF command line
aafcli> ns list name org.openecomp        
will give you the details of namespace org.openecomp

to get list of instructions
>> Help  

To get list of permission types of the user
>> perm list user dgl@openecomp.org

Create and grant permissions
>> perm create org.openecomp.dmaapBC.mr.topic :topic.org.openecomp.dmaapBC.mytopic1 pub org.openecomp.dmaapBC.admin org.openecomp.dmaapBC.access

>> perm create org.openecomp.dmaapBC.mr.topic :topic.org.openecomp.dmaapBC.mytopic1 sub org.openecomp.dmaapBC.admin org.openecomp.dmaapBC.access






 
Useful commands and Links
------------------------------------------------
 
 
To remove docker images from server

To remove all images 
docker rmi $(docker images -q)

To remove single image
docker rmi –f image-id

To remove all container
docker rm $(docker ps -a -q)

To remove single container
docker rm –f container-id

To start & stop containers       
docker start container-id/name
docker stop container-id/name

To access files in a container or check the folders/file system
Docker exec –it container-name bash
You can check logs  go to logs folder

To clear all docker logs 
truncate -s 0 /var/lib/docker/containers/*/*-json.log



 
