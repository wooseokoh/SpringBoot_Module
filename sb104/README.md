## Import시 주의사항

- javax.persistence.Id와 org.springframework.data.annotation.Id의 차이

```
javax.persistence.Id: 관계형 DB에서 사용.

org.springframework.data.annotation.Id : JPA에 의해 지원되지 않는 Nosql이나 공통 persistence API를 가지지 않는 프레임워크에서 사용됨.
```