rootProject.name = "cl-log-server"

include(
    "cl-log-api",
    "cl-log-domain",
    "cl-log-global-utils",
    "cl-log-infrastructure"
)

// ✅ API 모듈을 메인 프로젝트로 명시
project(":cl-log-api").name = "cl-log-api"
