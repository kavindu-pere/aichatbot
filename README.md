# AI Chatbot

This application is a Spring Boot-based AI chatbot designed to provide intelligent conversational capabilities. It leverages advanced AI models and services to understand and respond to user inputs in a natural and meaningful way.

## Key Features

- **Natural Language Processing**: Utilizes state-of-the-art AI models to process and understand natural language inputs.
- **Spring Boot Framework**: Built using the robust and scalable Spring Boot framework.
- **API Documentation**: Integrated with SpringDoc OpenAPI for easy API documentation and exploration.
- **Containerized Deployment**: Supports Docker for containerized deployment, making it easy to set up and run the application in various environments.
- **Extensible and Configurable**: Easily extendable and configurable to suit different use cases and requirements.

## Technologies Used

- **Spring Boot**: Framework for building Java applications.
- **Java 21**: Programming language version.
- **LangChain4j**: AI library for natural language processing.
  - `langchain4j`
  - `langchain4j-ollama`
  - `langchain4j-milvus`
- **SpringDoc OpenAPI**: API documentation.
- **Lombok**: Reduces boilerplate code.
- **Docker**: Containerization platform.
- **Maven**: Build tool.
- **VS Code**: Integrated development environment.

## To TRY on your own

- Start Docker
- Run `ollama-standalone-docker-compose.yml`
- Access ollama shell with
    ```sh
    docker exec -it ollama
    ```
    - pull language model

    ```sh
    ollama pull llama3.2:3b-instruct-fp16
    ```
    - pull embedding model
    ```sh
    ollama pull nomic-embed-text:v1.5
    ```
- Run `milvus-standalone-docker-compose.yml`

After all the container related operations are completed,

- Start the Spring Boot application.

- Navigate to `http://localhost:8080/swagger-ui.html`


## To verify knowledge

Look for `docs/information.txt` in the class path.


