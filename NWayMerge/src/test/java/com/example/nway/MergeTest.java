package com.example.nway;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MergeTest {

	@Test
	public void testMergeEmpty() {
		// GIVEN
		Merger merger = new Merger();
		Stream<Integer> input = Collections.<Integer>emptyList().stream();
		
		// WHEN
		Stream<Integer> merged = merger.merge(input);
		
		// THEN
		List<Integer> collected = merged.collect(Collectors.toList());
		assertEquals(collected, Collections.<Integer>emptyList());
	}
}
