package org.depromeet.clog.server.infrastructure.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["org.depromeet.clog.server"])
@EnableJpaRepositories(basePackages = ["org.depromeet.clog.server"])
class JpaConfiguration
