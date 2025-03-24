package org.depromeet.clog.server.admin.api.crag.presentation

import org.depromeet.clog.server.admin.api.crag.application.SaveColor
import org.depromeet.clog.server.admin.api.crag.application.SaveCrag
import org.depromeet.clog.server.admin.api.crag.application.SaveGrade
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragColorDto
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragDto
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragGradeDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin")
class CragCommandController(
    private val saveCrag: SaveCrag,
    private val saveColor: SaveColor,
    private val saveGrade: SaveGrade
) {

    @PostMapping("/add/crags")
    fun addCrag(@ModelAttribute("crag") request: SaveCragDto.Request): String {
        saveCrag(request.sanitized())

        return "redirect:/admin/add/crags"
    }

    @PostMapping("/crags/add/colors")
    fun addCragColor(@ModelAttribute("color") request: SaveCragColorDto.Request): String {
        saveColor(request)

        return "redirect:/admin/crags/colors"
    }

    @PostMapping("/crags/{id}/add/grades")
    fun addCragGrade(@PathVariable id: Long, @ModelAttribute("grade") request: SaveCragGradeDto.Request): String {
        saveGrade(id, request)

        return "redirect:/admin/crags/$id/details"
    }
}
