package org.depromeet.clog.server.admin.api.crag.presentation

import org.depromeet.clog.server.admin.api.crag.application.GetAllColors
import org.depromeet.clog.server.admin.api.crag.application.GetAllCrags
import org.depromeet.clog.server.admin.api.crag.application.GetCrag
import org.depromeet.clog.server.admin.api.crag.application.GetGrades
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragColorDto
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragDto
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragGradeDto
import org.depromeet.clog.server.infrastructure.configuration.properties.KakaoMapProperties
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class CragQueryController(
    private val getCrag: GetCrag,
    private val getAllCrags: GetAllCrags,
    private val getAllColors: GetAllColors,
    private val getGrades: GetGrades,
    private val kakaoMapProperties: KakaoMapProperties
) {

    @GetMapping("/index")
    fun index(
        model: Model
    ): String {
        val result = getAllCrags()
        model.addAttribute("crags", result)

        return "admin/cragList"
    }

    @GetMapping("/add/crags")
    fun addCragPage(
        model: Model
    ): String {
        model.addAttribute("crag", SaveCragDto.Request())
        model.addAttribute("kakaoKey", kakaoMapProperties.restApiKey)

        return "admin/cragAdd"
    }

    @GetMapping("/crags/{id}/details")
    fun cragDetailsPage(
        @PathVariable id: Long,
        model: Model
    ): String {
        val crag = getCrag(id)
        val grades = getGrades(id)
        model.addAttribute("crag", crag)
        model.addAttribute("grades", grades)

        return "admin/cragDetails"
    }

    @GetMapping("/crags/colors")
    fun getCragColors(
        model: Model
    ): String {
        val result = getAllColors()
        model.addAttribute("colors", result)

        return "admin/colorList"
    }

    @GetMapping("/crags/add/colors")
    fun addCragColorPage(
        model: Model
    ): String {
        model.addAttribute("color", SaveCragColorDto.initForm())

        return "admin/cragColorAdd"
    }

    @GetMapping("/crags/{id}/add/grades")
    fun addCragGradePage(
        @PathVariable id: Long,
        model: Model
    ): String {
        model.addAttribute("cragId", id)
        model.addAttribute("grade", SaveCragGradeDto.initForm())
        model.addAttribute("colors", getAllColors)

        return "admin/cragGradeAdd"
    }
}
