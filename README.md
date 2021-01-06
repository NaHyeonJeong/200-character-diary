# Introduce "200 character diary"

## 기능
### 일기 입력 페이지
1. 날짜를 선택해 특정 날짜에 일기를 작성
2. 기분 정도를 체크할 수 있음
3. 글자수는 200자로 제한해서 입력 가능
### 일기 리스트 페이지
1. 작성했던 일기를 ListView로 보여줌
2. 일기를 저장, 메인 페이지로 이동 버튼 있음
### 지난 일기 확인 페이지
1. 예전에 작성했던 일기를 볼 수 있음
2. 메인 화면과 일기 리스트 페이지로 이동 가능한 버튼 있음
## 부족한 부분
1. 이미지 처리 실패... 수정 필요...<br>갤러리에서 가져온 사진을 SQLite에 경로로 저장하고 싶은데 **content 부분에서** 막히는 중...
2. 다크 모드로 넘어가면 이미지를 흰색 라인을 가진 이미지로 자동으로 바꾸고 싶은데 어떻게 해야할지 모르겠음...<br>메소드가 있어서 해봤는데 실패...
3. 데이터를 DB에 저장할 때 NULL 처리가 미흡함... 수정 필요...
4. 일기를 작성한 날짜 별로 정렬 필요
