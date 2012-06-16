/*
 * HumanTimeTest.java
 * 
 * Created on 06.10.2008
 * 
 * Copyright (c) 2008 Johann Burkard (<mailto:jb@eaio.com>) <http://eaio.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.eaio.util.text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

/**
 * Test case for {@link HumanTime}.
 * 
 * @author <a href="mailto:jb@eaio.com">Johann Burkard</a>
 * @version $Id: HumanTimeTest.java 323 2008-10-08 19:06:22Z Johann $
 * @see <a
 *      href="http://johannburkard.de/blog/programming/java/date-formatting-parsing-humans-humantime.html">Date
 *      Formatting and Parsing for Humans in Java with HumanTime</a>
 */
public class HumanTimeTest extends TestCase {

	public HumanTimeTest() {
		super();
	}

	public HumanTimeTest(String name) {
		super(name);
	}

	public void testD() {
		assertEquals("2 d", new HumanTime().d().d().getExactly());
		assertEquals("2 d", new HumanTime().d().h(-24).getExactly());
	}

	public void testY() {
		assertEquals("1 y", HumanTime.eval("-1Y").toString());
		assertEquals("320 y", HumanTime.eval("- 4 319 y  Y").y().toString());
	}

	public void testS() {
		assertEquals("2 s", HumanTime.eval("1 s s").s().toString());
		assertEquals("4 s", HumanTime.eval("2 s s").s().s().toString());
	}

	/**
	 * Test method for
	 * {@link com.eaio.util.text.HumanTime#eval(java.lang.CharSequence)}.
	 */
	public void testEval() {
		assertEquals("2 m", HumanTime.eval("2 m").getExactly());
		assertEquals("8 d 2 h 6 m", HumanTime.eval("2m8d2h4m").getExactly());
		assertEquals("8 d 2 h 6 m", HumanTime.eval("1m 1 m8d2 h2 m 2 m")
				.getExactly());
		assertEquals("8 d 2 h 45 m", HumanTime.eval("8d2 h5 m 40 m")
				.getExactly());
		assertEquals("42 ms", HumanTime.eval("42ms").toString());
		assertEquals("4 ms", HumanTime.eval("1 ms 1ms 1  ms 1 ms s").toString());
		assertEquals("4 ms", HumanTime.eval("1  ms 1 ms s").ms(-2).toString());
		assertEquals("2 d 5 m", HumanTime.eval("1 d 1d 2m 3m").getExactly());
	}

	/**
	 * Test method for {@link com.eaio.util.text.HumanTime#exactly(long)}.
	 */
	public void testExactlyLong() {
		assertEquals("42 ms", new HumanTime(42).getExactly());
		assertEquals("1 s 42 ms", new HumanTime(1042).getExactly());
	}

	public void testApproximately() {
		assertEquals("42 ms", new HumanTime(42).getApproximately());
		assertEquals("1 s", new HumanTime(1042).getApproximately());
		assertEquals("1 y", HumanTime.eval("350 d 18 h").getApproximately());
		assertEquals("1 y", HumanTime.eval("365 d 8 h").getApproximately());
		assertEquals("1 y", HumanTime.eval("380 d 14 h 20 m 40s")
				.getApproximately());
		assertEquals("2 y", HumanTime.eval("700 d").getApproximately());
		assertEquals("50 d", HumanTime.eval("50d 20 s").getApproximately());
		assertEquals("3 d", HumanTime.eval("2d 22h").getApproximately());
		assertEquals("2 d", HumanTime.eval("2 d 1 h 20 m 50 s")
				.getApproximately());
		assertEquals("2 d 8 h", HumanTime.eval("2 d 8 h 20 m 50 s")
				.getApproximately());
		assertEquals("2 d 9 h", HumanTime.eval("2d9h").getApproximately());
		assertEquals("2 d 10 h", HumanTime.eval("2 d 5h 5h").getApproximately());
		assertEquals("2 h 20 m", HumanTime.eval("1 h 1h 10m 10m")
				.getApproximately());
		assertEquals("2 d", HumanTime.eval("1 d 1d 30 m").getApproximately());
		assertEquals("2 d", HumanTime.eval("1 d 1d 30 m").getApproximately());
		assertEquals("30 m", HumanTime.eval("0 h 10 m 10 m 10m")
				.getApproximately());
		assertEquals("1 h", HumanTime.eval("55m").getApproximately());
		assertEquals("1 m", HumanTime.eval("55 s").getApproximately());
		assertEquals("20 s", HumanTime.eval("19 s 850 ms").getApproximately());
		assertEquals("1 h", HumanTime.eval("29 m 30m 100 ms")
				.getApproximately());
		assertEquals("1 h", HumanTime.eval("30m 30m 100ms").getApproximately());
		assertEquals("20 s 200 ms", HumanTime.eval("20 s 200 ms")
				.getApproximately());
		assertEquals("1 y", HumanTime.eval("1 y 1 h").getApproximately());
		assertEquals("1 y", HumanTime.eval("1 y 10 h").getApproximately());
		assertEquals("1 y 171 d",
				HumanTime.eval("356 d 180 d 12 h 40 m 20 s 802 ms")
						.getApproximately());
		assertEquals("1 d", HumanTime.eval("23 h 1h 10 s").getApproximately());
	}

	public void testEquals() {
		assertEquals(HumanTime.eval("4d20m1s"),
				HumanTime.eval(" 4 d20 m 1  s "));
		assertFalse(HumanTime.eval("4d20m2s").equals(
				HumanTime.eval(" 4 d20 m 1  s ")));
		assertFalse(HumanTime.eval("4d20m1s").equals(
				HumanTime.eval(" 4 d20 m 1  d ")));
	}

	public void testGetStateChar() {
		assertEquals(HumanTime.State.IGNORED, HumanTime.getState(' '));
		assertEquals(HumanTime.State.IGNORED, HumanTime.getState('a'));
		assertEquals(HumanTime.State.NUMBER, HumanTime.getState('0'));
		assertEquals(HumanTime.State.NUMBER, HumanTime.getState('9'));
		assertEquals(HumanTime.State.UNIT, HumanTime.getState('y'));
		assertEquals(HumanTime.State.UNIT, HumanTime.getState('d'));
	}

	public void testHashCode() throws CloneNotSupportedException {
		assertEquals(0, new HumanTime(0L).hashCode());
		assertTrue(HumanTime.eval("42 s").hashCode() != 0);
		assertEquals(HumanTime.eval("42 s").hashCode(), HumanTime.eval(" 42s ")
				.clone().hashCode());
	}

	public void testSerialization() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ObjectOutputStream oOut = new ObjectOutputStream(bOut);
		oOut.writeObject(new HumanTime(42));
		oOut.close();
		ObjectInputStream oIn = new ObjectInputStream(new ByteArrayInputStream(
				bOut.toByteArray()));
		assertEquals(new HumanTime(42), oIn.readObject());
	}

}
