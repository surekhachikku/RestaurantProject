#!/bin/bash

# Bash Script to Start All Microservices
# Run this script from the RestaurantProject root directory

echo "========================================"
echo "Starting All Microservices"
echo "========================================"
echo ""

# Get the project root directory
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Function to start a service
start_service() {
    local service_name=$1
    local path=$2
    local port=$3
    
    echo "[INFO] Starting $service_name on port $port..."
    cd "$PROJECT_ROOT/$path" || exit 1
    mvn spring-boot:run > /dev/null 2>&1 &
    local pid=$!
    echo "✓ $service_name started (PID: $pid)"
    sleep 8
}

# Step 1: Eureka Server
echo "[1/8] Starting Eureka Server..."
start_service "Eureka Server" "eurekaserver/eurekaserver" 8761
echo "Waiting for Eureka to be ready..."
sleep 15

# Step 2: Config Server
echo "[2/8] Starting Config Server..."
start_service "Config Server" "configserver" 8888
echo "Waiting for Config Server to be ready..."
sleep 15

# Step 3: Auth Service
echo "[3/8] Starting Auth Service..."
start_service "Auth Service" "authservice/authservice" 8085
sleep 10

# Step 4: API Gateway
echo "[4/8] Starting API Gateway..."
start_service "API Gateway" "apigateway/apigateway" 8086
sleep 10

# Step 5: Restaurant Service
echo "[5/8] Starting Restaurant Service..."
start_service "Restaurant Service" "restaurantservice/restaurantservice" 9091
sleep 5

# Step 6: Food Catalogue Service
echo "[6/8] Starting Food Catalogue Service..."
start_service "Food Catalogue" "foodcatalogue/foodcatalogue" 9093
sleep 5

# Step 7: Order Service
echo "[7/8] Starting Order Service..."
start_service "Order Service" "orderservice/orderservice" 9094
sleep 5

# Step 8: User Service
echo "[8/8] Starting User Service..."
start_service "User Service" "userservice/userservice" 9092

echo ""
echo "========================================"
echo "All Services Started!"
echo "========================================"
echo ""
echo "Service URLs:"
echo "  Eureka Dashboard: http://localhost:8761"
echo "  Config Server:    http://localhost:8888"
echo "  Auth Service:     http://localhost:8085"
echo "  API Gateway:      http://localhost:8086"
echo "  Restaurant:       http://localhost:9091"
echo "  Food Catalogue:   http://localhost:9093"
echo "  Order Service:    http://localhost:9094"
echo "  User Service:     http://localhost:9092"
echo ""
echo "Wait 30-60 seconds for all services to fully start."
echo "Then check Eureka Dashboard to verify all services are UP."
echo ""
echo "To stop all services, press Ctrl+C or kill the Java processes."

