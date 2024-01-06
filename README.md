# MOVIE APP

### Quick Start

To run the application, use the following command:

```shell
./gradlew build && docker-compose up -d
```

### Gradle

This project uses Gradle as its build tool.

To build the project, use the following command:

```shell
./gradlew build
```

To clean the build directory, use the following command:

```shell
./gradlew clean
```

### Docker Compose

This project contains a Docker Compose file named `docker-compose.yaml`.

If you update the project and need to rebuild the Docker image, use the following command:

```shell
docker-compose build
```

and then run the application using:

```shell
docker-compose up -d
```

and then stop the application using:

```shell
docker-compose down
```