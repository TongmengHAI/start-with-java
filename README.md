# Spring Boot + Redis Rate Limiter

A simple **fixed-window rate limiter** built with **Spring Boot 3** and **Redis**.  
It limits the number of requests per client (by IP or API key) within a configurable time window.

---

## ✨ Features

- Redis-backed, **distributed safe** (works across multiple app instances)
- **Fixed-window algorithm** (100 requests per 60s by default)
- Configurable via `application.properties`
- Supports **per-IP** or **per-API key** identification
- Returns **HTTP 429 Too Many Requests** when exceeded
- Exposes rate limit headers:
    - `X-RateLimit-Limit`
    - `X-RateLimit-Remaining`
    - `X-RateLimit-Reset`
    - `Retry-After`

---

## 📦 Dependencies
- Java 17+
- Redis 5+
- Spring Boot Starter Web
- Spring Boot Starter Data Redis
- Spring Boot Configuration Processor
- Lombok (optional, for boilerplate reduction)
- Spring DevTools (optional, for development convenience)


---

## ⚙️ Configuration

```properties
server.port=8082
spring.application.name=ratelimiter-example

spring.data.redis.host=127.0.0.1
spring.data.redis.port=6379
spring.data.redis.timeout=2s

ratelimit.enabled=true
ratelimit.limit=100
ratelimit.window-seconds=60
ratelimit.bucket-name=api
# ratelimit.header-name=X-API-Key
```
## ▶️ Running the Application

### 1. Run a Redis server locally or use a hosted Redis service.
  - Can find more details at https://redis.io/docs/getting-started/installation/

  - Or use Docker: ```docker run -d -p 6379:6379 redis ```

  - Or manually installation by extract the ``` Redis-x64-5.0.14.1.rar ```  file
  - Then run
    ```bash 
      redis-server.exe 
    ``` 
    
### 2. Clone the repository and navigate to the project directory.

```bash
  git clone 


