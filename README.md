# Web Crawler Demo

This is a web crawler demo that uses web scraping to extract and filter information from [Hacker News](https://news.ycombinator.com/).

This project is developed in Java 8 using Jsoup as the web scraping library and Spring Boot 2.0.1 to expose the filtering operations as a RESTful API.

## Why Spring Boot?
Spring Boot is collection of libraries which let us create standalone, production-grade Spring based applications without all the hassle and configuration a standard Spring application suffers. Everything is auto-configured so we can focus on development rather than infrastructure and configuration. 

## Requirements
- Java 8 JDK
- Gradle
- Make sure port 8080 is open and not being used.

## Instructions
1. Open a console or terminal windows in the root folder of the project.
2. Execute: `./gradlew build bootRun` (Linux, OSX) to compile, test and run the application. Do not close the terminal window as the application resides in an embedded Tomcat server.
3. Go to [http://localhost:8080/web-crawler/entries](localhost) to see the first 30 entries.
4. Go to [http://localhost:8080/web-crawler/entries/filterMoreThanFiveWords](localhost) to filter all previous entries with more than five words in the title ordered by amount of comments (desc).
5. Go to [http://localhost:8080/web-crawler/entries/filterLessThanSixWords](localhost) to filter all previous entries with less than or equal to five words in the title ordered by points (desc).
6. Spring Boot uses an embedded Tomcat server, to stop the application just close the terminal window or hit `CTRL+C` .

You can either go to the browser or test the endpoints in any REST capable tool like postman or even through console with `curl`. Note that specifying an invalid endpoint through postman or curl, it will return a json response. For browser clients, there is a “whitelabel” error view that renders the same data in HTML format.