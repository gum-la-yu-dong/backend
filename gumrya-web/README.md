# Web

## 모듈 설명 
- 사용자를 위한 기능을 제공하는 어플리케이션 모듈.
- gumrya-core 모듈을 사용하여 데이터를 조회.
- gumrya-common 모듈의 공통 예외 사용

## 기술 스택 
- spring-boot-starter-web
    - spring-webmvc 프레임워크를 활용하여 Model-View-Controller 구조 설계
- JUnit5
- ```java
  'org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.4.RELEASE' 
  'org.springframework.restdocs:spring-restdocs-mockmvc:2.0.4.RELEASE'
  ``` 
    - Spring REST Docs를 활용한 api 문서화
- MockMvc를 활용한 Controller Layer 테스트 작성
