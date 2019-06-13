package com.example.demo.application.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.application.dtos.ActorDTO;
import com.example.demo.application.dtos.ActorShortDTO;

@FeignClient(name = "actor", url = "http://localhost:8002")
public interface ActorProxy {
	@GetMapping("/actores/lista")
	List<ActorShortDTO> getAll();
	@GetMapping("/actores/{id}")
	ActorDTO getOne(@PathVariable int id);

}
