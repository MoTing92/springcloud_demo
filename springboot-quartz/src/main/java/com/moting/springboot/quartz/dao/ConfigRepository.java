package com.moting.springboot.quartz.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moting.springboot.quartz.model.Config;

public interface ConfigRepository extends JpaRepository<Config, Long>{

}
