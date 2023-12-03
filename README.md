![CAMs Logo](https://github.com/dreonic/SC2002-CAMs/assets/66062290/8291ba8a-be45-4e54-9386-a067d2b68efe)

# Camp Application and Management System (CAMs)

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=flat&logo=Apache%20Maven&logoColor=white)

CAMS (Camp Application and Management System) is a Java Command Line Interface (CLI) application. It is made for staff and students to manage, view and register for camps within NTU. The app implements loosely coupled classes in multiple distinct packages. This makes our system easy to maintain, improve, and extendable.

## Building & Running from Source

1. Install the [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) (minimum version 17)
   and [Apache Maven](https://maven.apache.org/download.cgi).
2. Clone this repository to your machine and navigate to the project directory.

```bash
git clone https://github.com/dreonic/SC2002-CAMs.git
cd SC2002-CAMs
```

3. Clean existing builds, build the application, package the application into a Java Archive (`jar` file), and generate
   the documentation. All build outputs (`jar` and `class` files) are stored inside the target directory (`target/`). The
   Javadoc is generated at `target/site/apidocs/`.

```bash
mvn clean package site
```

4. Execute the application through the Java Archive (`jar` file).

```bash
java -jar target/SC2002-CAMs-2.0-SNAPSHOT.jar
```

5. View the Javadoc generated at `target/site/apidocs/index.html`.
