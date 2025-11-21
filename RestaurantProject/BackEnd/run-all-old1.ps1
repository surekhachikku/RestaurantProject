# ========================================
# PowerShell Script to Start All Microservices
# ========================================
# Run this script from the root directory of your RestaurantProject

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Starting All Microservices (with mvn clean & install)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# -------------------------
# Function to start a service
# -------------------------
function Start-Service {
    param(
        [string]$ServiceName,
        [string]$Path,
        [int]$Port
    )

    if (!(Test-Path $Path)) {
        Write-Host "❌ ERROR: Path '$Path' does not exist. Cannot start $ServiceName." -ForegroundColor Red
        return
    }

    Write-Host "========================================" -ForegroundColor Yellow
    Write-Host "Building and Starting $ServiceName on port $Port..." -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Yellow

    cd $Path

    # Run mvn clean install first
    Write-Host "Running 'mvn clean install' for $ServiceName..." -ForegroundColor Cyan
    mvn clean install

    # Start service
    Write-Host "Running 'mvn spring-boot:run' for $ServiceName..." -ForegroundColor Cyan
    Start-Process mvn -ArgumentList "spring-boot:run" -NoNewWindow -PassThru
}

# -------------------------
# Get the project root directory
# -------------------------
$ProjectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

# -------------------------
# Start services in order
# -------------------------
# Eureka Server
Start-Service -ServiceName "Eureka Server" -Path "$ProjectRoot\eurekaserver\eurekaserver" -Port 8761
Start-Sleep -Seconds 20

# Config Server
Start-Service -ServiceName "Config Server" -Path "$ProjectRoot\configserver" -Port 8888
Start-Sleep -Seconds 20

# Auth Service
Start-Service -ServiceName "Auth Service" -Path "$ProjectRoot\authservice\authservice" -Port 8085
Start-Sleep -Seconds 15

# API Gateway
Start-Service -ServiceName "API Gateway" -Path "$ProjectRoot\apigateway\apigateway" -Port 8086
Start-Sleep -Seconds 15

# Restaurant Service
Start-Service -ServiceName "Restaurant Service" -Path "$ProjectRoot\restaurantservice\restaurantservice" -Port 9091
Start-Sleep -Seconds 15

# Food Catalogue Service
Start-Service -ServiceName "Food Catalogue Service" -Path "$ProjectRoot\foodcatalogue\foodcatalogue" -Port 9093
Start-Sleep -Seconds 15

# Order Service
Start-Service -ServiceName "Order Service" -Path "$ProjectRoot\orderservice\orderservice" -Port 9094
Start-Sleep -Seconds 15

# User Service
Start-Service -ServiceName "User Service" -Path "$ProjectRoot\userservice\userservice" -Port 9092
Start-Sleep -Seconds 15

# -------------------------
# All services started
# -------------------------
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "All Services Started!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Press any key to stop all services..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

# Stop all Java processes (microservices) safely
Write-Host "Stopping all microservices..." -ForegroundColor Red
Get-Process java | Where-Object { $_.Path -like "*java*" } | Stop-Process -Force
Write-Host "All services stopped." -ForegroundColor Green
