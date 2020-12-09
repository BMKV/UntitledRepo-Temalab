package bme.aut.untitledtemalab.backend.api

import bme.aut.untitledtemalab.backend.api.model.Job
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(Job.SizeEnum.StringToSizeEnumConverter())
        registry.addConverter(Job.StatusEnum.StringToStatusEnumConverter())
    }
}