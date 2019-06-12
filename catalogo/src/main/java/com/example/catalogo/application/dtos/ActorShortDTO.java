package com.example.catalogo.application.dtos;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShortDTO {
	int getActorId();
	
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getFullName();
}
