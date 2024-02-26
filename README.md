# Spring Boot Keycloak OAuth2 Demo

This repository contains an example OAuth2 resource server and client implemented using Spring Boot, along with a Next.js frontend.

## Overview

The project demonstrates the integration of Keycloak as an identity manager and authorization server.

## Components

- **Resource Server**: A Spring Boot application serving as the backend API.
- **OAuth2 Client**: Another Spring Boot application acting as the client consuming the resource server's API.
- **Frontend**: The client's frontend is developed using Next.js.

## Setup

## Keycloak

1. Install Keycloak using Docker.
2. Create a realm named "acme-corporation".
3. Define the following "realm roles":
   - ACME_CORPORATION_ADMIN
   - ACME_CORPORATION_USER
4. Create test users, assigning one user to each role for testing purposes.

## Prerequisites

- Java JDK
- Node.js
- Docker

## Contributing
Contributions are welcome! Suggestions for improvements and corrections are encouraged.
