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
    docker-compose up -d postgres
    ```

2.  **Run the Java Application:**

    Next, clone this repository and run the Spring Boot application.

    ```bash
    git clone https://github.com/ncumtweb/ncumt-java.git
    cd ncumt-java/ncumt
    ./gradlew bootRun
    ```

    The application will be available at `http://localhost:8080`.
