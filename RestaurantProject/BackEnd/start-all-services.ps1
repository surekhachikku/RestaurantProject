# PowerShell Script to Start All Microservices
# Run this script from the RestaurantProject root directory

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Starting All Microservices" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Function to start a service
function Start-Service {
    param(
        [string]$ServiceName,
        [string]$Path,
        [int]$Port
    )
    
    Write-Host "Starting $ServiceName on port $Port..." -ForegroundColor Green
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$Path'; Write-Host 'Starting $ServiceName...' -ForegroundColor Yellow; mvn spring-boot:run"
    Start-Sleep -Seconds 8
}

# Get the project root directory
$ProjectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

# Step 1: Eureka Server
Write-Host "[1/8] Starting Eureka Server..." -ForegroundColor Yellow
Start-Service -ServiceName "Eureka Server" -Path "$ProjectRoot\eurekaserver\eurekaserver" -Port 8761
Write-Host "✓ Eureka Server started. Waiting for it to be ready..." -ForegroundColor Green
Start-Sleep -Seconds 15

# Step 2: Config Server
Write-Host "[2/8] Starting Config Server..." -ForegroundColor Yellow
Start-Service -ServiceName "Config Server" -Path "$ProjectRoot\configserver" -Port 8888
Write-Host "✓ Config Server started. Waiting for it to be ready..." -ForegroundColor Green
Start-Sleep -Seconds 15

# Step 3: Auth Service
Write-Host "[3/8] Starting Auth Service..." -ForegroundColor Yellow
Start-Service -ServiceName "Auth Service" -Path "$ProjectRoot\authservice\authservice" -Port 8085
Write-Host "✓ Auth Service started." -ForegroundColor Green
Start-Sleep -Seconds 10

# Step 4: API Gateway
Write-Host "[4/8] Starting API Gateway..." -ForegroundColor Yellow
Start-Service -ServiceName "API Gateway" -Path "$ProjectRoot\apigateway\apigateway" -Port 8086
Write-Host "✓ API Gateway started." -ForegroundColor Green
Start-Sleep -Seconds 10

# Step 5: Restaurant Service
Write-Host "[5/8] Starting Restaurant Service..." -ForegroundColor Yellow
Start-Service -ServiceName "Restaurant Service" -Path "$ProjectRoot\restaurantservice\restaurantservice" -Port 9091
Write-Host "✓ Restaurant Service started." -ForegroundColor Green
Start-Sleep -Seconds 5

# Step 6: Food Catalogue Service
Write-Host "[6/8] Starting Food Catalogue Service..." -ForegroundColor Yellow
Start-Service -ServiceName "Food Catalogue" -Path "$ProjectRoot\foodcatalogue\foodcatalogue" -Port 9093
Write-Host "✓ Food Catalogue Service started." -ForegroundColor Green
Start-Sleep -Seconds 5

# Step 7: Order Service
Write-Host "[7/8] Starting Order Service..." -ForegroundColor Yellow
Start-Service -ServiceName "Order Service" -Path "$ProjectRoot\orderservice\orderservice" -Port 9094
Write-Host "✓ Order Service started." -ForegroundColor Green
Start-Sleep -Seconds 5

# Step 8: User Service
Write-Host "[8/8] Starting User Service..." -ForegroundColor Yellow
Start-Service -ServiceName "User Service" -Path "$ProjectRoot\userservice\userservice" -Port 9092
Write-Host "✓ User Service started." -ForegroundColor Green

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "All Services Started!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Service URLs:" -ForegroundColor Yellow
Write-Host "  Eureka Dashboard: http://localhost:8761" -ForegroundColor White
Write-Host "  Config Server:    http://localhost:8888" -ForegroundColor White
Write-Host "  Auth Service:     http://localhost:8085" -ForegroundColor White
Write-Host "  API Gateway:      http://localhost:8086" -ForegroundColor White
Write-Host "  Restaurant:       http://localhost:9091" -ForegroundColor White
Write-Host "  Food Catalogue:   http://localhost:9093" -ForegroundColor White
Write-Host "  Order Service:    http://localhost:9094" -ForegroundColor White
Write-Host "  User Service:     http://localhost:9092" -ForegroundColor White
Write-Host ""
Write-Host "Wait 30-60 seconds for all services to fully start." -ForegroundColor Yellow
Write-Host "Then check Eureka Dashboard to verify all services are UP." -ForegroundColor Yellow
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

