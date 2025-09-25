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

## ▶️ Running the Application

### 1. Run a Redis server locally or use a hosted Redis service.
  - Can find more details at https://redis.io/docs/getting-started/installation/

  - Or use Docker: ```docker run -d -p 6379:6379 redis ```

  - Or manually installation by extract the ``` Redis-x64-5.0.14.1.rar ```  file. Then run
    ```bash 
      redis-server.exe 
    ``` 
    
### 2. Clone the repository and navigate to the project directory.
- Link: https://github.com/TongmengHAI/start-with-java/tree/rate_limit_redis

### 3. Configuration

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

### 4. Build and run the application.
```bash
  ./mvnw spring-boot:run
```
### 5. Test the rate limiter.
- Use curl or Postman to send requests to the `/api/data` endpoint.
```bash
  curl -i http://localhost:8082/api/data
```
Or run script `loop_req.sh` with Git Bash or Linux terminal:
```bash
  loop_req.sh
```
- You should see the rate limit headers in the response.
- After exceeding the limit, you will receive a `429 Too Many Requests` response.
- Example response headers:
```
HTTP/1.1 200 OK
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 99
X-RateLimit-Reset: 60
```
```HTTP/1.1 429 Too Many Requests
Retry-After: 30
```
- You can adjust the `ratelimit.limit` and `ratelimit.window-seconds` properties to test different rate limits.
- Try sending requests with different IPs or API keys (if configured) to see how the rate limiter behaves.
- Use tools like Apache JMeter or Postman to simulate high request rates and observe the rate limiting in action.
- Check the Redis database to see the keys and their expiration times.
- Monitor the application logs for rate limiting events.
- Integrate with a frontend application to see how the rate limiting affects user experience.

