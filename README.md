# Leonbets Parser 🏟️

Asynchronous sports betting data parser for [leonbets.com](https://leonbets.com/), written in Java.

## 📌 Description

This project fetches prematch odds and market information from Leonbets' internal API across the following sports:
- ⚽ Football
- 🎾 Tennis
- 🏒 Ice Hockey
- 🏀 Basketball

Each parser:
- Runs asynchronously using a thread pool (max 3 threads)
- Retrieves events from top leagues only
- Writes structured output to sport-specific log files

## 🧪 Technologies

- Java 17+
- Maven
- Jackson (for JSON parsing)
- JUnit 5 (for unit testing)
- CompletableFuture (for concurrency)

## 🏃‍♂️ How to Run

```bash
mvn clean compile exec:java -Dexec.mainClass="com.jsparser.MainParser"
