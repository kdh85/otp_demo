# OTP 번호 생성 및 인증하기
## OTP 요건.
* 4자리의 정수를 각 사용자에게 중복없이 고유하게 할당함.
* 발급된 번호의 만료시간은 3분을 기준으로 설정.
* 3분이내에 발급된 번호와 인증요청된 번호가 일치하면 해당 번호 발급을 회수 후 다음 사용자에게 재발급.

## 전체 시나리오.
* 사용자가 앱을 로그인.
* 정맥 단말기에서 정맥을 등록 시도시 앱의 OTP값을 요청.
* 정맥 단말기에 앱에서 제공하는 OTP값을 입력 후 검증 시도후 성공/실패 케이스 진행.
  * 성공
    * 발급된 OTP 번호를 redis 에서 제거.
    * 성공값을 return.
  * 실패 
    * 발급된 OTP 키값의 유효시간이 남은 경우
      * 발급된 OTP값을 redis에서 유지.
      * 불일치로 인한 실패를 return.
    * 입력하는 순간 OTP가 갱신되어 불일치하게 되는 경우.
      * 갱신된 발급된 OTP값을 redis에서 유지.
      * 불일치로 인한 실패를 return.
## 기능 항목
* OTP 생성 방식
  * 사용자id, 발급시 사용하는 timestamp, secretKey를 사용하여 otp번호를 생성.
    * otp 생성주기를 설정값은 서버에서 관리.
* 생성과정에 필요한 기능 항목
  * 서버
    * 사용자단말기에서 otp를 생성하기 위해 사용할 timestamp 와 생성주기 값을 전달.
    * 사용자가 인증을 시도할때 사용자id, secretKey, 생성된 otp 번호를 받아서 서버사이드에서 값을 생성 후 검증결과 반환. 
  * 사용자단말기
    * otp 메뉴 접근시 서버에 최초 번호 생성을 위한 timestamp와 생성주기 정보를 요청.
    * 자신이 가지고 있는 사용자 정보(사용자id, secretKey)와 제공받은 timestamp, 생성주기로 otp번호 생성.
    * 생성주기별로 반복적으로 화면에서 신규 otp 번호를 노출.