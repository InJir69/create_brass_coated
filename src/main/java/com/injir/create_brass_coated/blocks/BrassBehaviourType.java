package com.injir.create_brass_coated.blocks;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;

public class BrassBehaviourType<B extends BrassTileEntityBehaviour> {

	private String name;

	public BrassBehaviourType(String name) {
		this.name = name;
	}

	public BrassBehaviourType() {
		this("");
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return super.hashCode() * 31 * 493286711; // Better hash table distribution
	}
}
