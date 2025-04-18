## 🙌 Hello
**클로그 Server 팀**은 클라이밍 기록을 쉽고 편리하게 관리할 수 있는 서비스, **클로그**의 백엔드를 개발 및 운영합니다.  
안정적인 서비스 제공을 위해 시스템 설계, 개발, 배포, 모니터링 전반을 책임지고 있습니다.

<br>

## 🧩 Tech Stack

| 구분 | 기술 |
|------|------|
| **Language** | Kotlin |
| **Framework** | Spring Boot |
| **Database** | MySQL |
| **Infra** | NCP, S3 |
| **CI / CD** | GitHub Actions, Kubernetes, ArgoCD |
| **Monitoring** | Elastic APM, Kibana |


<br>

## Project Setting
### detekt
```bash
git config core.hooksPath .githooks
chmod +x .githooks/pre-commit
```
<br>

## 🏛️ System Architecture
<img width="927" alt="스크린샷 2025-04-05 02 06 35" src="https://github.com/user-attachments/assets/541ef47f-821e-4e70-bc1f-7be29b983c92" />

- 서비스는 Naver Cloud Platform (NCP) 기반에서 운영되며, Kubernetes 환경에서 배포 및 관리됩니다.
- CI/CD 파이프라인은 GitHub Actions와 ArgoCD를 통해 자동화되어 있습니다.
- 서비스 모니터링은 Elastic Stack을 활용하여 로그 수집, 분석, APM 트레이싱을 수행합니다.

<br>

## 🏗️ Application Architecture
- Domain-Driven Design (DDD) 기반으로 설계되었으며, 명확한 도메인 경계를 통해 유지보수성과 확장성을 확보했습니다.
- 멀티 모듈 구조로 구성되어 각 도메인의 관심사를 분리하고 독립적인 개발이 가능하도록 했습니다.
- 주요 모듈은 다음과 같습니다:
  ``` bash
  📁 clog-backend/
  ├── clog-admin/            # 어드민 기능 제공 모듈
  ├── clog-api/              # 클라이언트 요청을 처리하는 API 모듈
  ├── clog-domain/           # 도메인 모델 정의 (Repository interface 등)
  ├── clog-infrastructure/   # 외부 API 연동, DB 연동 등의 구현체
  ├── clog-global-utils/     # 공통 유틸 등
  ```
<br>

## 🚨 Monitoring / Alert
> 다양한 지표와 로그를 효과적으로 수집하고 시각화하여 **서비스 가용성**과 **성능 최적화**에 기여합니다.
- 각 도구는 독립적이면서도 통합적으로 관리되어 **유연한 확장성**과 **효율적인 문제 진단**을 지원합니다.
- Elastic APM: 전체 요청 흐름 트레이싱 및 병목 탐지
- Logstash → Elasticsearch: 로그 수집 및 저장
- Kibana: 실시간 로그 및 성능 데이터 시각화
- 장애 탐지 및 이슈 발생 시 Slack 알림 연동 예정

<br>

## 🧑‍💻 Contribution
<div align=center>

| 김대원 | 권기준 | 최혜미 |
|:---:|:---:|:---:|
| <a href="https://github.com/big-cir"> <img src="https://avatars.githubusercontent.com/u/99483390?v=4" width=100px alt="_"/> </a> | <a href="https://github.com/kkjsw17"> <img src="https://avatars.githubusercontent.com/u/39583312?v=4" width=100px alt="_"/> </a> | <a href="https://github.com/ghrltjdtprbs"> <img src="https://avatars.githubusercontent.com/u/105612931?v=4" width=100px alt="_"/> </a> |
| **Server** (Lead) | **Server** | **Server** |

</div>

<br>

## 📎 기타 참고 자료
🔗 [API 명세서 (Swagger)](https://your-link-to-swagger.com)  
🗂️ [시스템 다이어그램 (작성 예정)](https://your-link-to-system-diagram.com)  
📝 [기술 블로그 포스팅 (작성 예정)](https://your-link-to-blog.com)  