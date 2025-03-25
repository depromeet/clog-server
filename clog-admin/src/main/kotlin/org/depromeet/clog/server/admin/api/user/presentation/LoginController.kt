package org.depromeet.clog.server.admin.api.user.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class LoginController {

    companion object {
        private const val LOGIN_PAGE = "admin/login"
    }

    @GetMapping("/login")
    fun loginPage(): String = LOGIN_PAGE
}
