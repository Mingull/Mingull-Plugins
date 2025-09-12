---
applyTo: "**"
---

# Requirements

## Lombok

This project uses [Lombok](https://projectlombok.org/) to reduce boilerplate code. Ensure your IDE has the Lombok plugin installed and annotation processing enabled.
always try to use Lombok for data classes and builders.

## Supported Operating Systems

The project is developed and tested on Windows, macOS, and Linux. Ensure your environment matches one of these platforms for best compatibility.

## Java Version

This project is built using JDK 24 or higher. Make sure your development environment is set up with JDK 24 to ensure compatibility.

## IDE Recommendations

We recommend using IntelliJ IDEA or Eclipse for the best development experience. Make sure to enable annotation processing and install the Lombok plugin.

## Gradle

This project uses Gradle version 9.0.0 or higher. Ensure you have the correct version of Gradle installed to manage dependencies and build the project.
You can check your Gradle version by running:

```bash
gradle --version
```

### Building the Project

To build the project, navigate to the project directory and run:

```bash
./gradlew clean build
```

or

```bash
gradle clean build
```

## Dependency Management

All dependencies are managed via Gradle. Do not manually add JARs to the project. Use the `build.gradle` files to declare dependencies.

## Code Style

Follow the default Java code style for your IDE. Use 4 spaces for indentation. Organize imports and format code before committing. Prefer Lombok annotations for data classes and builders.

## Testing

Tests should be placed in the appropriate `src/test/java` directories. To run all tests, use:

```bash
./gradlew test
```

or

```bash
gradle test
```

## Documentation

Javadoc comments are required for all public classes and methods. To generate documentation, run:

```bash
./gradlew javadoc
```

## Configuration

If the project requires environment variables or configuration files, sample files should be provided in the `resources` directory. Document any required variables in the project README.

## Contributing

Before submitting a pull request, ensure your code builds and passes all tests. Follow the code style and document your changes. Report issues or feature requests via the project's issue tracker.
