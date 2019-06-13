package com.example.demo.application.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDTO implements Serializable {
	private int actorId;
	private String firstName;
	private String lastName;

}
