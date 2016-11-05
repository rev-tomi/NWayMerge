package com.example.nway;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		assertEquals(Collections.<Integer>emptyList(), collected);
	}

	@Test
	public void testMergeOneStream() {
		// GIVEN
		Merger merger = new Merger();
		List<Integer> inputList = Arrays.asList(1, 2, 3);
		Stream<Integer> input = inputList.stream();
		
		// WHEN
		Stream<Integer> merged = merger.merge(input);
		
		// THEN
		List<Integer> collected = merged.collect(Collectors.toList());
		assertEquals(inputList, collected);
	}
}
