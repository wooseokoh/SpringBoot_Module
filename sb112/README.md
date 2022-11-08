```
1. 레디스 설치
- https://github.com/microsoftarchive/redis/releases
- Redis-x64-3.0.504.msi (window)

레디스는 항상 켜져있음..

# 레디스 시작
redis-server --service-start

# 레디스 접속
redis-cli.exe

# 레디스 종료
redis-cli.exe shutdwon

# 레디스 모든 정보 확인
keys *

# 정보보기
reids-cli.exe info

# help
redis-cli.exe help

# 모니터링
redis-cli.exe monitor
```