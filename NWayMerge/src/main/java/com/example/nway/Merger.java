package com.example.nway;

import java.util.Collections;
import java.util.stream.Stream;

public class Merger {

	public <T extends Comparable<T>> Stream<T> merge(Stream<T>... inputs) {
		if (inputs.length == 0) {
			return Collections.<T>emptyList().stream();
		}
		return inputs[0];
	}

}
