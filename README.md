# Qualcomm 5g Data ingestion Svc
Qualcomm - Data ingestion Svc

[![Build Status](http://localhost:8080/job/data-ingestion-svc/) ]

* [Description](#desc)
* [Tools and Technologies](#tools)
* [CICD](#cicd)
* [HealthChecks](#health)


## Overview ##
Service to ingest Patent application numbers into SQS for processing.
<a name="desc"></a>
## Description

This service provides restful API to feed in Application Patent numbers.
(Send multiple numbers spltting by comma)

Request:

```http request
POST /ingest/queue HTTP/1.1
Host: http://localhost:8081
Content-Type: application/json

US14458933
```

Response:

```
200 OK
Request is being processed
```



<a name="tools"></a>
## Tool and Technologies
    Codebase:  Java - 1.8.x / Springboot - 2.x.x
    Build/Store: Maven / Docker / Artifactory
    Eventbus: Amazon SQS
    Database: Oracle / RDS
    CICD: Jenkins

<a name="endpoints"></a>
## HealthChecks
| Environments|
| :----| 
| [Local](http://localhost:8081/actuator/health) |

## How to run this application locally

To run this application on your local, please follow the steps below:

* mvn clean install

* Run [DataIngestionSvcApplication](/Users/sravindra1/code/personal/data-ingesion-svc/src/main/java/com/personal/dataingestionsvc/DataIngestionSvcApplication.java) class. No special VM arguments are needed.

## How to run Jenkins locally
Start Jenkins :

* docker run -p 8080:8080 -p 50000:50000 -v ~/jenkins_home:/var/jenkins_home jenkins/jenkins:lts

## How to run Oracle-Docker locally
Start Oracle :
* docker run -d -p 1521:1521 --name oracle store/oracle/database-enterprise:12.2.0.1-slim