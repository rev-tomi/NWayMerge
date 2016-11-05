package com.example.nway;

import java.util.stream.Stream;

public class Merger {

	public <T extends Comparable<T>> Stream<T> merge(Stream<T>... inputs) {
		return inputs[0];
	}

}
