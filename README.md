# **Geo Location API service**

A simple web application to manage the basic sales operations

## Technologies Used
<li>Programing Language: java (version 17)</li>
<li>Framework: SpringBoot  (version 3.2.2)</li>
<li>DB: Postgres</li>

## Getting Started

### Prerequisites

Before you begin, ensure you have the following prerequisites installed on your machine:

- [Java]   (V 17)
- [Maven]  (V 3.6.3)
- [Postgresql] (any version you wish)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/AhmadBahlwan/GeoLocatorServer.git



2. Navigate to the project directory:

   ```bash
   cd geolocator

3. Create database with name geolocator :


4. modify these properties in application.properties file as below :

   ```bash
   spring.datasource.url=jdbc:postgresql://${database-host}:${database-port}/geolocator
   spring.datasource.username={your user name}
   spring.datasource.password={your password}
  

5. Run the project:

   ```bash
   mvn spring-boot:run -Dallowed.origins="${frontend app link}" -Dgeocode.api.url="https://geocode.search.hereapi.com/v1/geocode?lang=en&apiKey=${you api key}" -DEMAIL_USERNAME="${your email}" -DEMAIL_PASSWORD="${you email password}"


## Now You can access the api using the link
- http://localhost:8080/geolocation/api/v1/submit-address
- request body: {
  "address": "an address",
  "email": "an email"
  }


## Deployment
<li>AWS ElasticBeanstalk</li>

## You can try it on this link
- http://geo-locator.us-east-1.elasticbeanstalk.com/geolocation/api/v1/submit-address
- request body: {
  "address": "an address",
  "email": "an email"
  }