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

`JAVA`,`Spring Boot`,`Spring Security`,`Docker`,`MySQL`, `Github`,`Git`,`Slack`,`Redis`



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
- 회원 단위테스트 코드 작성

# 🤔 Learned

- 시큐리티를 적용해봄으로써 Dispatcher Servlet 이전에 작동하는 시큐리티 Filter에 대해 이해하게 되었다
- 로그인 인증을 위해 JWT 토큰을 사용하였는데 JWT 토큰이 쿠키/세션에 비해 왜 효율적인지에 대해 이해하게 되었다.
- Exception Handler를 통한 공통 예외 처리를 직접 해봄으로써 예외 처리의 집중화 및 @Valid를 통한 유효성 체크에 대한 흐름을 알게 되었다.
ERRORCODE 및 VALIDATIONCODE를 ENUM 타입으로 관리하여 유지보수를 편하게 할 수 있다는 것을 알게 되었다.
- API를 호출하였을때 공통 API RESPONSE를 적용하여 공통적으로 결과값을 받을 수 있도록 처리할 수 있게 되었다.
- 직접 DB 설계를 해봄으로써 DB에 대해서 어떻게 설계를 해야되는지에 대해 이해하게 되었다.
- 단위 테스트 코드를 작성하고 통과시킴으로써 버그 발견 및 수정에 빠르다는 것을 알게 되었고 단위 테스트의 로직 코드들을 리팩토링 하여도 여전히 테스트가 통과한다면 
수정한 로직 코드들은 원하는대로 잘 동작을 한다는 것이기에 리팩토링에도 도움이 된다는 것을 알았다.


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








