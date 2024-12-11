# ✨DoToDo
---
## Introduction
 기존의 to do list 어플리케이션은 단순한 할 일 관리에만 머물러 있어 사용자가 각 일정의 우선순위를 설정해 실천하거나 일정을 최적화하는 데에 많은 노력을 들여야 한다.<br/>
**DoToDo**는 **“해야 할 일을 행동으로 옮기자”** 라는 의미를 담고 있으며, 사용자가 계획한 일을 적극적으로 실천할 수 있도록 할 일 관리와 AI 기반 일정 생성 기능을 결합한 안드로이드 애플리케이션이다.<br/>
DoToDo는 사용자의 할 일 목록을 관리하고, 우선순위와 마감일을 고려하여 최적화된 주간 일정을 자동으로 생성해주는 서비스를 제공한다.<br/>

---
## Contents
[- How to use](#How-to-use)
[- Key Features & Usage](Key-Features-&-Usage)
[- Tech Stack](#Tech-Stack)
[- Reference](#Reference)
[- License](#License)

---
## How to use

---
## Key Features & Usage
**1. 구성요소**
   ![dotodo5](https://github.com/szoyoung/DoToDo/blob/main/image/dotodo5.png)
   - 초기화면
   - ai scheduling 화면
   - to do list 화면
   - 설정 화면

**2. To Do List**
   ![dotodo3](https://github.com/szoyoung/DoToDo/blob/main/image/dotodo3.png)
   - 사용자는 할 일 목록을 입력할 수 있다
   - 할 일이 추가되면 짧은 터치로 내용을 수정 가능하다
   - 긴 터치를 통해 할 일의 세부 사항, 우선순위, 마감일을 입력할 수 있다
   - to do list는 완료 여부, 우선순위, 마감일에 따라 정렬된다
   - 알림 기능을 켰을 경우, 완료되지 않은 항목이 있다면 하루 전에 상단의 팝업을 통해 알림을 받을 수 있다

**3. AI Scheduling**
   ![dotodo4](https://github.com/szoyoung/DoToDo/blob/main/image/dotodo4.png)
   - 사용자가 입력한 to do list를 기반으로 ai가 스케줄링을 할 수 있다
   - 오른쪽 상단의 '+' 버튼을 누르면 스케줄이 자동 생성된다
     - 스케줄을 생성한 날짜로부터 일주일의 스케줄이 생성된다
     - ai는 할 일의 세부 사항, 우선 순위, 마감일, 고정 스케줄 등을 고려한다
     - 스케줄이 생성된 후 저장 버튼을 누르면 스케줄이 저장되며 새로운 스케줄을 생성하기 전까지 ai schduling 화면에서 확인가능하다
    
**4. Settings**
   ![dotodo6](https://github.com/szoyoung/DoToDo/blob/main/image/dotodo6.png)
   - 사용자는 설정 화면에서 자신의 고정 스케줄을 입력할 수 있다
   - 미완료 된 할일의 상단 푸시 알림 기능을 설정할 수 있다
---
## Tech Stack
⭐️Language: Java<br/>
⭐️Platform: Android<br/>
⭐️Networking:Retrofit2<br/>
⭐️Architecture & Components<br/>
   - Architecture Pattern: MVVM (Model-View-ViewModel)
   - Android Jetpack Components:
   - Room Database : local database
   - LiveData
---
## Reference
- ChatGPT 4.0
- <https://ai.google.dev/gemini-api/docs?hl=ko>
---
## License
Apache License 2.0
