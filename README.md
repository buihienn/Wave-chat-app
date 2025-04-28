# WaveChatApp

## Overview
WaveChatApp is a chat application designed to provide seamless communication between users. It includes features such as user authentication, real-time messaging, and group chat functionality.

## Notes
This project was developed using **NetBeans IDE**, which provides an integrated environment for building and managing Java applications.

##  Some basic features
- User authentication and profile management.
- Real-time messaging between users.
- Group chat support.
- User-friendly interface with intuitive design.
- Database integration for storing chat history and user data.

## Project Structure
```
WaveChatApp/
├── db.properties          # Database configuration file
├── pom.xml                # Maven project configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── wavechat/   # Java source code for the application
│   │   ├── resources/
│   │       ├── database/
│   │       │   └── chat_database.sql  # SQL script for database setup
│   │       ├── images/                # Application icons and images
│   │           ├── add_black.png
│   │           ├── logo.png
│   │           └── ...
```

## Requirements
- Java 11 or higher
- Maven 3.6 or higher
- A relational database (e.g., MySQL)
- NetBeans IDE (for development)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/WaveChatApp.git
   cd WaveChatApp
   ```
2. Open the project in **NetBeans IDE**:
   - Launch NetBeans.
   - Go to `File > Open Project` and select the `WaveChatApp` folder.

3. Configure the database:
   - Update `db.properties` with your database credentials.
   - Run the SQL script located at `src/main/resources/database/chat_database.sql` to set up the database.

4. Build the project using Maven:
   ```bash
   mvn clean install
   ```

## Usage
1. Run the application:
   ```bash
   java -jar target/WaveChatApp.jar
   ```
2. Access the application through the provided interface.

## Resources
- **Images**: Located in `src/main/resources/images/`, used for UI design.
- **Database Script**: `src/main/resources/database/chat_database.sql` for initializing the database.

## Authors
Developed by WaveTeam - Bui Hien and Ho Phu Vinh (students of University of HCMUS).

