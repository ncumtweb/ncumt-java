# NCUMT Java Project

This document provides instructions on how to set up and run this project locally.

## Prerequisites

*   [Git](https://git-scm.com/)
*   [Docker](https://www.docker.com/)
*   [JDK 25](https://jdk.java.net/25/)

## Setup and Run

1.  **Start the PostgreSQL Database:**

    First, clone the `ncumt-docker` repository and start the PostgreSQL container using Docker Compose.

    ```bash
    git clone https://github.com/Ben901227/ncumt-docker.git
    cd ncumt-docker
    docker-compose up -d db
    ```

2. **Configure Local Environment:**

   Copy the `application-local.template` file to `application-local.properties` in the `src/main/resources` directory.

   ```bash
   cp src/main/resources/application-local.template src/main/resources/application-local.properties
   ```

   Then, open `src/main/resources/application-local.properties` and fill in your `client-id` and `client-secret`. You
   can obtain them by following this
   guide: [How to get client-id and client-secret](https://www.notion.so/ncumt/2399d08eb09a4a96b0dcbad8a1c3fc84?v=b7cf3aa6492f456f81588df3f776cce1&source=copy_link#f124632e354147139525cf3303f5403a).

   ```properties
   # src/main/resources/application-local.properties

   spring.security.oauth2.client.registration.bael.client-id=YOUR_CLIENT_ID
   spring.security.oauth2.client.registration.bael.client-secret=YOUR_CLIENT_SECRET

   ```

3. **Run the Java Application:**

    Next, clone this repository and run the Spring Boot application.

    ```bash
    git clone https://github.com/ncumtweb/ncumt-java.git
    cd ncumt-java/ncumt
    ./gradlew bootRun
    ```

    The application will be available at `http://localhost:8080`.
