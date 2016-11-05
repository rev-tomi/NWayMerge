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
	public void testMergeNoStream() {
		// GIVEN
		Merger merger = new Merger();
		
		// WHEN
		Stream<Integer> merged = merger.merge();
		
		// THEN
		List<Integer> collected = merged.collect(Collectors.toList());
		assertEquals(Collections.<Integer>emptyList(), collected);
	}

	@Test
	public void testMergeEmptyStream() {
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

	@Test
	public void testMergeTwoStreams() {
		// GIVEN
		Merger merger = new Merger();
		List<Integer> inputList1 = Arrays.asList(1, 3, 5);
		List<Integer> inputList2 = Arrays.asList(2, 4, 6);
		
		// WHEN
		Stream<Integer> merged = merger.merge(inputList1.stream(), inputList2.stream());
		
		// THEN
		List<Integer> collected = merged.collect(Collectors.toList());
		assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), collected);
	}
}
