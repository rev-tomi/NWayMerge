package com.example.nway;

import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 * @author tamas.rev
 * 
 * A class for implementing the n-way merge with the Heap data structure.
 *
 */
public class Merger {

	public <T extends Comparable<T>> Stream<T> merge(Stream<T>... inputs) {
		if (inputs.length == 0) {
			return Collections.<T>emptyList().stream();
		}
		final PriorityQueue<MergeInputHolder<T>> heap = new PriorityQueue<>(inputs.length);
		initializeHeap(heap, inputs);
		final Iterator<T> iterator = new HeapMergeIterator<>(heap);
		Iterable<T> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	private <T extends Comparable<T>> void initializeHeap(final PriorityQueue<MergeInputHolder<T>> heap, Stream<T>... inputs) {
		for (Stream<T> input : inputs) {
			final MergeInputHolder<T> holder = new MergeInputHolder<T>(input);
			if (! holder.isEmpty()) {
				heap.add(holder);
			}
		}
	}
	
	/**
	 * A private class for comparing the head of the input and stepping over it if necessary.
	 * 
	 * @author tamas.rev
	 *
	 * @param <T> a comparable value.
	 */
	private static class MergeInputHolder<T extends Comparable<T>> implements Comparable<MergeInputHolder<T>> {
		
		private T head;
		private final Iterator<T> tail;
		
		public MergeInputHolder(Stream<T> input) {
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
		public int compareTo(MergeInputHolder<T> other) {
			assert head != null : "The current value cannot be null.";
			assert other != null : "The holder of the other value cannot be null.";
			assert other.head != null : "The other value cannot be null.";
			return head.compareTo(other.head);
		}
		
		
	}
	
	/**
	 * A private class for retrieving the next value from the right stream
	 * 
	 * @author tamas.rev@gmailo.com
	 *
	 * @param <T> a comparable value.
	 */
	private static class HeapMergeIterator<T extends Comparable<T>> implements Iterator<T> {
		
		private final PriorityQueue<MergeInputHolder<T>> queueOfStreams;
		
		public HeapMergeIterator(PriorityQueue<MergeInputHolder<T>> queueOfStreams) {
			this.queueOfStreams = queueOfStreams;
		}

		@Override
		public boolean hasNext() {
			return ! queueOfStreams.isEmpty();
		}


		@Override
		public T next() {
			MergeInputHolder<T> holder = queueOfStreams.poll();
			T result = holder.getHead();
			if (holder.step()) {
				queueOfStreams.add(holder);
			}
			return result;
		}
		
	}

}
