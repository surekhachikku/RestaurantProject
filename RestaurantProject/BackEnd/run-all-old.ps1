# ========================================
# PowerShell Script to Start All Microservices
# ========================================
# Run this script from the root directory of your RestaurantProject

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Starting All Microservices" -ForegroundColor Cyan
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

    Write-Host "Starting $ServiceName on port $Port..." -ForegroundColor Green

    $LogFile = Join-Path $Path "service.log"

    # Start service in a new PowerShell window
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$Path'; Write-Host 'Starting $ServiceName...' -ForegroundColor Yellow; mvn spring-boot:run 2>&1 | Tee-Object -FilePath '$LogFile'"
}

# -------------------------
# Get the project root directory
# -------------------------
$ProjectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

# -------------------------
# Start services in order
# -------------------------

# 1️⃣ Eureka Server
Start-Service -ServiceName "Eureka Server" -Path "$ProjectRoot\eurekaserver\eurekaserver" -Port 8761
Write-Host "Waiting 30 seconds for Eureka Server to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 2️⃣ Config Server
Start-Service -ServiceName "Config Server" -Path "$ProjectRoot\configserver" -Port 8888
Write-Host "Waiting 30 seconds for Config Server to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 3️⃣ Auth Service
Start-Service -ServiceName "Auth Service" -Path "$ProjectRoot\authservice\authservice" -Port 8085
Start-Sleep -Seconds 10

# 4️⃣ API Gateway
Start-Service -ServiceName "API Gateway" -Path "$ProjectRoot\apigateway\apigateway" -Port 8086
Start-Sleep -Seconds 10

# 5️⃣ Restaurant Service
Start-Service -ServiceName "Restaurant Service" -Path "$ProjectRoot\restaurantservice\restaurantservice" -Port 9091
Start-Sleep -Seconds 10

# 6️⃣ Food Catalogue Service
Start-Service -ServiceName "Food Catalogue Service" -Path "$ProjectRoot\foodcatalogue\foodcatalogue" -Port 9093
Start-Sleep -Seconds 10

# 7️⃣ Order Service
Start-Service -ServiceName "Order Service" -Path "$ProjectRoot\orderservice\orderservice" -Port 9094
Start-Sleep -Seconds 10

# 8️⃣ User Service
Start-Service -ServiceName "User Service" -Path "$ProjectRoot\userservice\userservice" -Port 9092
Start-Sleep -Seconds 10

# -------------------------
# All services started
# -------------------------
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
Write-Host "Logs for each service can be found in their respective 'service.log' files." -ForegroundColor Yellow
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
