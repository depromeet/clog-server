package org.depromeet.clog.server.admin.api.presentation

import org.depromeet.clog.server.admin.api.application.AdminService
import org.depromeet.clog.server.admin.api.presentation.dto.SaveCrag
import org.depromeet.clog.server.admin.api.presentation.dto.SaveCragColor
import org.depromeet.clog.server.admin.api.presentation.dto.SaveCragGrade
import org.depromeet.clog.server.infrastructure.configuration.properties.KakaoMapProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService,
    private val kakaoMapProperties: KakaoMapProperties
) {

    companion object {
        private const val LOGIN_PAGE = "admin/login"
    }

    @GetMapping("/login")
    fun loginPage(): String = LOGIN_PAGE

    @GetMapping("/index")
    fun index(model: Model): String {
        val result = adminService.getAllCrag()
        model.addAttribute("crags", result)

        return "admin/cragList"
    }

    @GetMapping("/add/crags")
    fun saveCragPage(model: Model): String {
        model.addAttribute("crag", SaveCrag.Request())
        model.addAttribute("kakaoKey", kakaoMapProperties.restApiKey)

        return "admin/cragAdd"
    }

    @PostMapping("/add/crags")
    fun saveCrag(@ModelAttribute("crag") request: SaveCrag.Request): String {
        val sanitized = request.sanitized()
        adminService.createCrag(sanitized)

        return "redirect:/admin/add/crags"
    }

    @GetMapping("/crags/{id}/details")
    fun cragDetailsPage(@PathVariable id: Long, model: Model): String {
        val crag = adminService.getCrag(id)
        val grades = adminService.getGrades(id)
        model.addAttribute("crag", crag)
        model.addAttribute("grades", grades)

        return "admin/cragDetails"
    }

    @GetMapping("/crags/colors")
    fun getCragColors(model: Model): String {
        val result = adminService.getAllColor()
        model.addAttribute("colors", result)

        return "admin/colorList"
    }

    @GetMapping("/crags/add/colors")
    fun saveCragColorPage(model: Model): String {
        model.addAttribute("color", SaveCragColor.initForm())

        return "admin/cragColorAdd"
    }

    @PostMapping("/crags/add/colors")
    fun saveCragColorPage(@ModelAttribute("color") request: SaveCragColor.Request): String {
        adminService.createColor(request)

        return "redirect:/admin/crags/colors"
    }

    @GetMapping("/crags/{id}/add/grades")
    fun saveCragGradePage(@PathVariable id: Long, model: Model): String {
        model.addAttribute("cragId", id)
        model.addAttribute("grade", SaveCragGrade.initForm())
        model.addAttribute("colors", adminService.getAllColor())

        return "admin/cragGradeAdd"
    }

    @PostMapping("/crags/{id}/add/grades")
    fun saveCragGrade(@PathVariable id: Long, @ModelAttribute("grade") request: SaveCragGrade.Request): String {
        adminService.createGrade(id, request)

        return "redirect:/admin/crags/$id/details"
    }
}
