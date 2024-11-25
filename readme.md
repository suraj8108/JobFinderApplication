# JOBHunt - Job Finder Application 🕵️‍♂️💼

## Project Description 📄
JOBHunt is a comprehensive job hunting application that categorizes users as Candidates and Employers. Employers can post job applications, and Candidates can apply to these applications accordingly.

## Library Dependencies 📚
- **JWT**: JWT Authorization is implemented for both Candidates and Employers.
- **Swagger**: Swagger configuration is added to generate the API specifications.
- **SonarQube**: Used for code coverage and generating reports.

## Deployment Tools 🚀
1. **Docker**: Used for containerizing the application.
2. **Kubernetes**: Manages the deployment of Docker containers.

A pipeline is created to:
- Generate the JAR file.
- Create the Docker image.
- Push the image to the Docker repository.
- Kubernetes pulls the latest image and deploys it accordingly.

## Getting Started 🛠️
### Prerequisites
- Docker
- Kubernetes
- Java Development Kit (JDK)
- Maven

### Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/JobHunt.git
    cd JobHunt
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    java -jar target/JobHunt-0.0.1-SNAPSHOT.jar
    ```

### Docker Deployment
1. **Build Docker image**:
    ```bash
    docker build -t jobhunt:latest .
    ```

2. **Run Docker container**:
    ```bash
    docker run -p 8080:8080 jobhunt:latest
    ```

### Kubernetes Deployment
1. **Apply Kubernetes configurations**:
    ```bash
    kubectl apply -f k8s/
    ```

2. **Check the status**:
    ```bash
    kubectl get pods
    ```

For more details, refer to the PPT attached in the repository.

---

Feel free to reach out if you have any questions or need further assistance!
