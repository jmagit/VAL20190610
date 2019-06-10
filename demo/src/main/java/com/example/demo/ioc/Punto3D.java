package com.example.demo.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
@Qualifier("3D")
public class Punto3D extends Punto {
	int z = 3;

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Punto3D [X=" + getX() + ", Y=" + getY() + ", Z=" + z + "]";
	}
	
	
}
