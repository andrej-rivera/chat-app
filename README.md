# Communication Web App

This project is an attempt to recreate the features of current, industry standard communication applications like Discord, Microsoft Teams, and Slack in a simple web-app format. The main goals of this project is to create an application where users can text, call, or video chat with each other.

## _Planned_ Main Features

The following are the main features that are planned to be implemented, numbered in order of importance.

1. User Account Creation and Login

   - users should have a username, password, and userID (so that multiple users can share the same username)
   - users should be able to 'friend'/connect with each other

2. Text Chat

   - users that are connected to each other can communicate via text chat
   - plan to implement using WebSockets API, store messages in a database
   - messages should persist throughout sessions and both users in a chat should have access to chat history

3. Voice and Video Chat
   - users that are connected to each other can communicate via a voice call
   - users can also share the visual output of a connected camera (video chat)
   - plan to implement using the webRTC library, no need to store anything in database for this part

## Technology Stack

The technologies used for the project as well as the reasonings behind their usage are listed below.

### Frontend

**React + Vite** - Framework for Dynamic Sites

- we will be using Vite for their HMR and fast setup features
- utilize React Router for page navigation

**TypeScript** - Frontend Programming Language

**Tailwind CSS + shadCN** - Component library & CSS Framework

- shadCN will be used for rapid frontend development
- we can make edits to the pre-built shadCN UI components using Tailwind

**WebRTC** - real time communication library - open-soruce and allows for voice, and video calls (also screen-sharing!)

### Backend

**PostgresSQL** - versatile SQL database

- need to handle large volumes of data
- need to be able to scale so that many messages can be sent at the same time
- for future features, we may need to change the schema of the datasets

**Java 25** - latest LTS java version

- I have prior experience in Java so I'm most comfortable with this

**Spring Boot** - Spring framework for developing APIs

- we utilize a framework so that it's faster to get everything up and running
- I have little experience with this, so it'll just be learn as I go

**Spring WebSocket** - WebSocket library for persistent connections

- websocket allows for realtime communication between client & server (text-chat!)

**JUnit 5** - Testing Framework for APIs

### Infrastructure

Docker - Container Software

- for easy setup of backend & frontend
- additionally, allows for easier manual testing
