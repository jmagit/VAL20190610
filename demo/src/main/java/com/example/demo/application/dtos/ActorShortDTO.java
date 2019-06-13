package com.example.demo.application.dtos;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class ActorShortDTO {
	int actorId;
	String fullName;
}
