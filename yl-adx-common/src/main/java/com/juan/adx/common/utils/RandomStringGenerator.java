package com.juan.adx.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

public final class RandomStringGenerator {

	private static final char[] PRINTABLE_CHARACTERS = "ABCDEF0123456789"
			.toCharArray();

	private static final int DEFAULT_MAX_RANDOM_LENGTH = 12;

	private final SecureRandom randomizer = new SecureRandom();

	private final int maximumRandomLength;

	public RandomStringGenerator() {
		this( DEFAULT_MAX_RANDOM_LENGTH );
	}

	public RandomStringGenerator( final int maxRandomLength ) {
		this.maximumRandomLength = maxRandomLength;
	}

	public String getNewString() {
		final byte[] random = getNewStringAsBytes();
		return convertBytesToString( random );
	}
	
	public String getNewString(int length) {
		final byte[] random = getNewStringAsBytes(length);
		return convertBytesToString( random );
	}

	public byte[] getNewStringAsBytes() {
		final byte[] random = new byte[ this.maximumRandomLength ];
		this.randomizer.nextBytes( random );
		return random;
	}
	
	public byte[] getNewStringAsBytes(int length) {
		final byte[] random = new byte[ length ];
		this.randomizer.nextBytes( random );
		return random;
	}

	private String convertBytesToString( final byte[] random ) {
		final char[] output = new char[ random.length ];
		for( int i = 0; i < random.length; i++ ) {
			final int index = Math.abs( random[ i ] % PRINTABLE_CHARACTERS.length );
			output[ i ] = PRINTABLE_CHARACTERS[ index ];
		}
		return new String( output );
	}

	public int getMaxLength() {
		return this.maximumRandomLength;
	}
	
	public static void main(String[] args) {
		RandomStringGenerator generator = new RandomStringGenerator();
		System.out.println(generator.getNewString(32));
		
		int newCapacity = 10 + (10 >> 1);
		System.out.println(newCapacity);
		System.out.println(UUID.randomUUID());
	}
}