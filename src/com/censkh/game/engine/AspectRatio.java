package com.censkh.game.engine;

public enum AspectRatio {
	AR16to9("16:9"),AR16to10("16:10"),AR4to3("4:3");
	
	private final String name;
	
	 private AspectRatio(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	};
}
