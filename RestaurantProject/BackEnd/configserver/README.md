# Spring Cloud Config Server

## Overview
This is the centralized configuration server for all microservices in the Restaurant Project.

## Port
- **8888** (Default Spring Cloud Config Server port)

## Configuration Storage
Currently using **file system** (native profile) for development.
- Location: `./config-repo/`

## Configuration Files
- `restaurantservice.properties` - Restaurant Service configuration
- `foodcatalogue.properties` - Food Catalogue Service configuration
- `orderservice.properties` - Order Service configuration
- `userservice.properties` - User Service configuration
- `application.properties` - Common configuration for all services

## How It Works

### 1. Config Server Flow
```
Service Startup
  ↓
Reads bootstrap.properties
  ↓
Connects to Config Server (via Eureka)
  ↓
Fetches configuration
  ↓
Merges with local application.properties
  ↓
Service starts with merged config
```

### 2. Configuration Priority
1. **Config Server** (highest priority)
2. **bootstrap.properties** (local)
3. **application.properties** (local, lowest priority)

### 3. Service Discovery
Config Server is registered with Eureka, so services can discover it automatically.

## Testing Config Server

### 1. Check if Config Server is running
```bash
curl http://localhost:8888/actuator/health
```

### 2. Get configuration for a service
```bash
# Get restaurantservice configuration
curl http://localhost:8888/restaurantservice/default

# Get specific profile
curl http://localhost:8888/restaurantservice/dev
```

### 3. Refresh configuration (without restart)
Services need to have `/actuator/refresh` endpoint enabled:
```bash
curl -X POST http://localhost:9091/actuator/refresh
```

## Production Setup

### Option 1: Git Repository (Recommended)
Update `application.properties`:
```properties
spring.profiles.active=git
spring.cloud.config.server.git.uri=https://github.com/your-org/config-repo
spring.cloud.config.server.git.clone-on-start=true
spring.cloud.config.server.git.force-pull=true
```

### Option 2: Vault (For Secrets)
```properties
spring.profiles.active=vault
spring.cloud.config.server.vault.host=127.0.0.1
spring.cloud.config.server.vault.port=8200
spring.cloud.config.server.vault.scheme=http
```

### Option 3: JDBC (Database)
```properties
spring.profiles.active=jdbc
spring.datasource.url=jdbc:mysql://localhost:3306/configdb
spring.cloud.config.server.jdbc.sql=SELECT key, value FROM config_properties WHERE application=? AND profile=? AND label=?
```

## Environment-Specific Configuration

Create profile-specific files:
- `restaurantservice-dev.properties` (Development)
- `restaurantservice-prod.properties` (Production)
- `restaurantservice-qa.properties` (QA)

Set profile in service:
```properties
spring.profiles.active=dev
```

## Security (Production)

1. **Encrypt sensitive data**:
```bash
# Encrypt a value
curl http://localhost:8888/encrypt -d "sensitive-value"

# Decrypt
curl http://localhost:8888/decrypt -d "encrypted-value"
```

2. **Add to config file**:
```properties
spring.datasource.password={cipher}encrypted-value
```

3. **Set encryption key**:
```properties
encrypt.key=your-encryption-key
```

## Troubleshooting

### Service can't connect to Config Server
1. Check if Config Server is running on port 8888
2. Check if Config Server is registered in Eureka
3. Verify `spring.cloud.config.discovery.service-id=config-server` in bootstrap.properties
4. Check network connectivity

### Configuration not loading
1. Verify service name matches config file name (case-sensitive)
2. Check config-repo directory exists and has files
3. Verify bootstrap.properties is present
4. Check logs for config server connection errors

### Configuration changes not reflected
1. Restart the service, OR
2. Use `/actuator/refresh` endpoint (if enabled)
3. Use Spring Cloud Bus for automatic refresh (advanced)

