# 💡 Topic

- **여행 여정을 기록과 관리하는 SNS 서비스**
- 회원이 서비스를 로그인하여 자신의 정보 관리 및 자신의 여행에 대한 정보와 여정들에 대한 정보를 작성할 수 있으며 다른 회원이 작성한 여행글을 조회 및 댓글,좋아요를 달 수 있는 서비스




# 📝 Summary

여행에 대한 정보를 나누는 SNS들처럼 회원으로 가입을 하게 되면 다른 사람들이 작성한 여행글을 볼 수 있으며 여행글에 대해 댓글과 좋아요 표시를 남길 수 있다.
또한, 자신이 다녀온 여행에 대한 정보와 여정들에 대한 정보를 작성할  수 있으며 회원 자신만의 정보를 조회,수정을 할 수 있도록 하는 SNS 서비스이다.




# ⭐️ Key Function

- 회원
    - 사람들이 자신의 아이디를 만들어 로그인할 수 있다.
    - 로그인 시간에 만료 시간을 두어 일정 시간이 지나면 서비스 사용을 위해 재로그인을 해야 한다.
    - 아이디와,비밀번호를 통하여 인증을 진행하며 일치하지 않을 경우 서비스 사용을 할 수 없다.
    - 자신의 정보를 조회 및 수정을 할 수 있다.
- 여행
    - 여행글을 등록할 수 있다.
    - 여행글들을 검색할 수 있다.
    - 여행을 조회하면서 여정들을 조회할 수 있다.
    - 여행글을 일부분 또는 전부를 수정할 수 있다.
    - 다른 사람의 여행글에 댓글 및 좋아요를 남길 수 있다.
- 여정
    - 여행에 여정을 등록할 수 있다.
    - 여행의 여정들을 조회할 수 있다.
    - 여정의 정보를 수정할 수 있다.   



# 🛠 Tech Stack

`JAVA`,`Spring Boot`,`Spring Security`,`Docker`,`MySQL`, `Github`,`Git`,`Slack`



# ⚙️ Architecture

`Domain Design Architecture`




# 🧑🏻‍💻 Team

- 백엔드 개발자 4명




# 🤚🏻 Part

- JWT 토큰을 이용한 로그인 구현 
- 회원 CRU API 개발
- 여행 CU API 개발
- 공통 예외 처리 구현
- 공통 API Response 처리 구현
- 시큐리티를 이용한 패스워드 암호화

  # 🤔 Learned

- 시큐리티를 적용해봄으로써 Dispatcher Servlet 이전에 작동하는 시큐리티 Filter에 대해 이해하게 되었다
- 로그인 인증을 위해 JWT 토큰을 사용하였는데 JWT 토큰이 쿠키/세션에 비해 왜 효율적인지에 대해 이해하게 되었다.
- Exception Handler를 통한 공통 예외 처리를 직접 해봄으로써 예외 처리의 집중화 및 @Valid를 통한 유효성 체크에 대한 흐름을 알게 되었다.
ERRORCODE 및 VALIDATIONCODE를 ENUM 타입으로 관리하여 유지보수를 편하게 할 수 있다는 것을 알게 되었다.
- API를 호출하였을때 공통 API RESPONSE를 적용하여 공통적으로 결과값을 받을 수 있도록 처리할 수 있게 되었다.
- 직접 DB 설계를 해봄으로써 DB에 대해서 어떻게 설계를 해야되는지에 대해 이해하게 되었다.

# 🔌 프로젝트 실행방법 


- 이 프로젝트에서는 코드 다운으로만 실행이 불가하고 DB가 Docker에 이미지 파일로 올라가 있기에 Docker 실행 방법이 필요하다. 
프로젝트를 실행해보기 위해서는 아래의 설정과 파일들이 필요하다.
<img width="649" alt="Untitled" src="https://github.com/Parkgeonmoo/Travel-Record2/assets/50697545/8c12f1cb-f58a-4796-89c1-b5f390b088a6">

그래서 위의 그림과 같은 관계를 가지고 동작한다.

1. SpringBoot 에서 3305Port로 DB관련 요청을 보낸다.
2. Docker Engine이  3305Port 요청을 받는다.
3. 설정에 따라 Docker Engine이 3305Port 요청을 MySQL의 포트인 3306Port로 전달해줌으로서 동작하게 된다.

## DB 연결 방법

### Docker 설치

도커를 실행시키기 위해서는 실행환경에 Docker가 설치되어있어야 한다.

Window의 경우 **WSL**이라는 Windows 운영체제에서 경량화된 가상화 기술을 사용하여 Linux 운영 체제를 구동할 수 있도록 해 주는 프로그램을 깐 후, DockerDesktop을 설치하면 된다.

아래 잘 정리된 설치 관련 블로그 글이다.

**WSL 설치**

[WSL2 사용 설정(윈도우에서 Ubuntu 사용하는 방법)](https://axce.tistory.com/110?category=1030982)

Docker Desktop 설치

[[Docker] 윈도우 도커 설치방법(window 11)](https://axce.tistory.com/121)

### 실행

Docker 설치를 마쳤으면 이제 Docker Container을 실행시킬 수 있다.

<img width="179" alt="스크린샷 2023-10-28 151421" src="https://github.com/Group5-toy/KDT_Y_BE_Toy_Project2/assets/97028441/6e236e2f-870f-448e-aee5-42190a926fba">

docker-compose.yml 파일에서 빨간상자로 표시된 실행버튼을 클릭하면 자동으로 MySQL 컨테이너를 생성하여 동작시킨다.

그렇게 되면 3305 포트로 연결되는 MySQL 서버가 동작하여 SpringBoot와 연결되어 동작하게 된다.

- 필요한 파일 리스트
1. MySQL의 DockerFile 
2. MySQL 초기설정을 위한 init.sql
3. MySQL 컨테이너 실행을 위한 docker-compose.yml 파일

### DockerFile

- docker_database라는 폴더안에 MySQL 도커 이미지 설정에 대한 파일들을 담아 두었다. 
그 중 하나인 DockerFile 이다.

```tsx
FROM mysql:latest

MAINTAINER team_five

COPY init.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD=root

VOLUME /mysql_data

EXPOSE 3306
```

### init.sql

- docker_database 폴더 안에 있는 MySQL 초기설정을 위한 init.sql 파일이다.

```tsx
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
flush privileges;

create database if not exists trip_record;

use trip_record;
```

### docker-compose.yml

- MySQL 컨테이너 실행을 위한 docker-compose.yml 파일이다. 
앞서 작성한 이미지, 초기 설정 파일들을 기반으로 생성된 이미지를 컨테이너화 해서 동작시킨다.

```tsx
version: '3'
services:
  mysql-server:
    container_name: trip-mysql-server
    build:
      context: ./docker_database
      dockerfile: Dockerfile
    ports:
      - "3305:3306"
```

해당 파일을 실행시킴으로서 독립적인 MySQL 서버가 생성되며 연결이 되어 프로젝트를 테스트해볼 수 있다.


# 📷 Screenshot

## User
### createUser
![유저 도메인_createUser](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/3b34f0ec-cee1-4bf0-860b-39888640cf94)
### entryAccessToken
![유저 도메인_entryAccessToken](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/0a1e6a21-8f54-4fe0-abb6-b6d57a20f03d)
### getUser
![유저 도메인_getUser](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/7f6a635d-b1d5-4320-bcd2-986fb2923ff3)
### loginUser
![유저 도메인_loginUser](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/ca1d9063-cef2-4046-a106-3e8c5bb3e5bc)
### patchUser
![유저 도메인_patchUser](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/2cfcf695-dacc-4a0c-86d2-6137d86c6c05)
### updateUser
![유저 도메인_updateUser](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/aa95b84d-6b46-485c-8b5d-e8b1e5f27a63)

## Trip
### trips-post
![trip_trips-post](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/fe216138-eecd-4362-863c-63c613b21a70)
### trips-all
![trip_trips-all](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/bbb26e7e-1ed2-4a23-acb0-8eec7efe11a0)
### trips-myAll(get)
![trip_trips-myAll(get)](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/43823092-339b-4df1-81c5-d0629e15b1c8)
### trips-search(get)
![trip_trps-search(get)](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/6c8cb143-f471-46d3-ae65-a9ca0aa4f509)

## Journey
### createJourney
![여정도메인_createJourney](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/af36205e-9b55-46e2-962c-549b6f8895e7)
### getAllJourneysByTrip
![여정도메인_getAllJourneysByTrip](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/362ed889-b083-4565-ac71-bd4ad71248cc)
### updateLodgmentJourney
![여정도메인_updateLodgmentJourney](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/e8cf4565-af9a-4072-af06-ca8329edf0c7)
### updateMoveJourney
![여정도메인_updateMoveJourney](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/af923120-72b4-4e1d-8a65-2fb869deb86d)
### updateVisitJourney
![여정도메인_updateVisitJourney](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/a9fd25b4-027f-4acd-bb35-60445e1ce235)

## Comment
### POST
![Comment_POST](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/3cc04c84-e153-45c3-b48a-e0a0bd5350fd)
### GET
![Comment_GET](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/c26c1bf7-b534-42a7-a343-436e2eecf675)
### PUT
![Comment_PUT](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/841ebce4-7478-41c9-942e-8c2b1e320f6b)
### DELETE
![Comment_DELETE](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/1bc7b6e5-2842-4e85-967d-9e4612a36977)

## Wish
### wishes(post)
![wish_wishes(post)png](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/c5a3d46f-b4de-4b54-ae10-03b51c477e61)
### wishes(delete)
![wish_wishes(delete)](https://github.com/Group5-toy/KDT_Y_BE_Toy_Project3/assets/97028441/1ea4c1c0-0586-4d47-b72c-4a3875b119cc)








