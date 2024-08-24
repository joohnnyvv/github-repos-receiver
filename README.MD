# GitHub Repositories Retriever API

## Overview

This project is a Spring Boot 3 RESTful API that allows users to retrieve information about GitHub repositories for a specific user. The API provides detailed information about repositories that are not forks, including the name of each branch and the last SHA approval. The app uses the GitHub API v3 as a backend service to retrieve the necessary data.

## Features

- **Retrieve Non-Fork Repositories**: The API lists all repositories owned by a specified GitHub user, excluding forked repositories.
- **Branch Information**: For each repository, the API returns branch details, including the branch name and the latest commit SHA.
- **Error Handling**: If the specified GitHub user does not exist, the API returns a 404 response with a structured error message.
- **Containerization**: The project includes a Dockerfile for easy deployment in a containerized environment.
- **Unit Tests**: The project includes tests for the controller class to ensure correct functionality and reliability.

## Authorization

For authentication on the GitHub API, add the generated token as a variable in the application.properties file. If you don't know how to generate a token, take a look at the [documentation](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens).

## API Endpoints

### Get User Repositories

**Endpoint:** `/api/github/repos/{username}`  
**Method:** `GET`  
**Headers:**
- `Accept: application/json`

**Response:**

- **200 OK**  
  Returns a JSON array of repositories with the following structure:

  ```json
  [
      {
          "name": "example-repo",
          "ownerLogin": "username",
          "branches": [
              {
                  "name": "main",
                  "lastCommitSha": "abc123..."
              },
              {
                  "name": "dev",
                  "lastCommitSha": "def456..."
              }
          ]
      }
  ]
  ```

- **404 Not Found**  
  If the specified GitHub user does not exist, returns the following JSON structure:

  ```json
  {
      "status": 404,
      "message": "User not found"
  }
  ```

## Installation

### Prerequisites

- Docker installed on your machine.

### Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/joohnnyvv/github-repos-receiver.git
    cd github-repos-receiver
    ```

2. Build the Docker image:

    ```bash
    docker build -t github-repo-receiver .
    ```

3. Run the Docker container:

    ```bash
    docker run -p 8080:8080 github-repo-receiver
    ```

4. The API will be available at `http://localhost:8080/api/github/repos/{username}`

## Code Standards

The codebase follows industry standards for RESTful API development, including:

- Clean and modular code with clear separation of concerns.
- Proper use of HTTP status codes.
- Consistent error handling and messaging.
- Comprehensive unit tests to ensure code quality and reliability.

---

For any questions or support, feel free to contact the me at janekrembikowski@gmail.com