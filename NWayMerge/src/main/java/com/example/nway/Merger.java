package com.example.nway;

import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Merger {

	public <T extends Comparable<T>> Stream<T> merge(Stream<T>... inputs) {
		if (inputs.length == 0) {
			return Collections.<T>emptyList().stream();
		}
		final PriorityQueue<StreamToMerge<T>> heap = new PriorityQueue<>(inputs.length);
		for (Stream<T> input : inputs) {
			final StreamToMerge<T> holder = new StreamToMerge<T>(input);
			if (! holder.isEmpty()) {
				heap.add(holder);
			}
		}
		final Iterator<T> iterator = new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return !heap.isEmpty();
			}

			@Override
			public T next() {
				StreamToMerge<T> holder = heap.poll();
				T result = holder.getHead();
				if (holder.step()) {
					heap.add(holder);
				}
				return result;
			}
			
		};
		Iterable<T> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	
	private static class StreamToMerge<T extends Comparable<T>> implements Comparable<StreamToMerge<T>> {
		
		private T head;
		private final Iterator<T> tail;
		
		public StreamToMerge(Stream<T> input) {
			tail = input.iterator();
			step();
		}
		
		public boolean step() {
			if (tail.hasNext()) {
				head = tail.next();
				return true;
			}
			return false;
		}
		
		public boolean isEmpty() {
			return head == null;
		}
		
		public T getHead() {
			return head;
		}
		
		@Override
		public int compareTo(StreamToMerge<T> other) {
			assert head != null : "The current value cannot be null.";
			assert other != null : "The holder of the other value cannot be null.";
			assert other.head != null : "The other value cannot be null.";
			return head.compareTo(other.head);
		}
		
		
	}

}
