package com.moting.springboot.quartz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Config {

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id;  

    @Column  
    private String cron;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
	
	public Config() {
		super();
	}

	public Config(Long id, String cron) {
		super();
		this.id = id;
		this.cron = cron;
	}  
    
}
