# Multi-Threaded Stock Trading Server

## Project Overview

This is an academic project which implements a multi-threaded server designed to handle and execute stock trading orders. It supports various types of buy and sell orders and ensures efficient and fair order matching. The server manages user portfolios and maintains data integrity through concurrent processing and synchronization techniques.

## Features

- **Order Types Supported:**
  - Sell a specified quantity of shares when the price reaches a certain threshold.
  - Sell shares if the price increases by more than 5%.
  - Purchase shares at a specific price.
  - Buy shares if the price falls below 5%.

- **User Management:**
  - User authentication and authorization.
  - Tracking of user portfolios and share holdings.
  - Real-time updates on order status and trade execution.

- **Order Management:**
  - Separate queues for buy and sell orders.
  - Persistent storage of orders and trade history.
  - Partial order fulfillment and queue management.

- **Concurrency and Synchronization:**
  - Multi-threaded request processing for handling multiple users simultaneously.
  - Synchronization mechanisms to ensure data integrity and prevent race conditions.
  - Thread-safe data structures for queue management and order processing.

### Key Components
1. **Server:**
   - Handles client connections and manages user sessions.
   - Uses multi-threading to process multiple requests concurrently.
   
2. **Order Processing:**
   - Implements trade matching algorithms to match buy and sell orders.
   - Utilizes thread-safe queues to manage and process orders.

3. **Synchronization:**
   - Ensures thread safety using locks, mutexes, or other synchronization primitives.
   - Manages access to shared resources to prevent race conditions and ensure data integrity.
