# URL_PARSING_API

- 입력 URL의 결과데이터를 영어 대문자, 소문자, 숫자 순서로 교차하여 새로운 문자열을 만든다.
- 요청 파라미터의 Unit단위로 Content를 생성
    - Remain은 나머지 값
- ex) ABCabc123456 은 A1a2B3b4C5c6

# Application 실행

``` bash
git clone https://github.com/Pawer0223/url_parsing_api.git;
cd url_parsing_api;
mvn package;
java -jar ./target/parsing-0.0.1-SNAPSHOT.jar;
```

# Test
- http://localhost:8080/swagger-ui.html 접속
- Swagger 문서를 통해 [Try it out] 버튼으로 실행
  
# Example1

### parameters
- type: 1
- unit: 100
- url: http://localhost:8080
    
### result
``` json
{
  "success": true,
  "response": {
    "content": "",
    "remain": "A1a1B1b2h3h455555"
  },
  "error": null
}
```

# Example2

### parameters
- type: 2
- unit: 50
- url: http://wrongurl.ccom

### result
```json
{
  "success": false,
  "response": null,
  "error": {
    "message": "http://wrongurl.ccom invalid URL",
    "status": 400
  }
}
```


# 구현방법
- [핵심 기능 구현 Class](https://github.com/Pawer0223/url_parsing_api/blob/main/src/main/java/com/example/parsing/utils/ParsingUtils.java)
- Jsoup 라이브러리를 사용하여 입력 URL 요청 및 Html태그 제거 기능 처리
    - `getUrlDataJsoup(String url)`
- 결과 데이터를 규칙에 맞게 오름차순 정렬 및 교차하여 문자열 생성
    - `parsing(String data, int type)`
