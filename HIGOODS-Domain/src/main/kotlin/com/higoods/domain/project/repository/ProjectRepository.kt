package com.higoods.domain.project.repository

import com.higoods.domain.project.domain.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long>
