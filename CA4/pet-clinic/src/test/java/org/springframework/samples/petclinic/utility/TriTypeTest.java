package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "Side1 <= 0")
@ClauseDefinition(clause = 'b', def = "Side2 <= 0")
@ClauseDefinition(clause = 'c', def = "Side3 <= 0")
@ClauseDefinition(clause = 'd', def = "Side1+Side2 <= Side3")
@ClauseDefinition(clause = 'e', def = "Side2+Side3 <= Side1")
@ClauseDefinition(clause = 'f', def = "Side1+Side3 <= Side2")
class TriTypeTest {

	private static final Logger log = LoggerFactory.getLogger(TriTypeTest.class);

	@Test
	public void sampleTest() {
		TriType tryType = new TriType();
		TriType.TryClass triClass;
		triClass = tryType.classifyTriangle(1,1,1);
		log.debug("triangle identified as {}", triClass);
		assertEquals(TriType.TryClass.EQUILATERAL, triClass);
	}

	public boolean firstPredicate(int Side1, int Side2, int Side3) {
		if(Side1 <= 0 || Side2 <= 0 || Side3 <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean secondPredicate(int Side1, int Side2, int Side3) {
		if(Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2) {
			return true;
		} else {
			return false;
		}
	}

	//	--------------------	CUTPNFP COVERAGE ------------------------------------------

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	a's unique true point
	@UniqueTruePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest() {
		assertTrue(firstPredicate(-1, 1, 1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(-1,1,1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	a's near false point
	@NearFalsePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest0() {
		assertFalse(firstPredicate(3,4,5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	b's unique true point
	@UniqueTruePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest1() {
		assertTrue(firstPredicate(1, -1, 1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(1,-1,1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	b's near false point
	@NearFalsePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest2() {
		assertFalse(firstPredicate(3,4,5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	c's unique true point
	@UniqueTruePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void cutpnfpCoverTest3() {
		assertTrue(firstPredicate(1, 1, -1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(1,1,-1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	c's near false point
	@NearFalsePoint(
		predicate = "a + b + c‌",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest4() {
		assertFalse(firstPredicate(3,4,5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	d's unique true point
	@UniqueTruePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f‌",
		implicant = "d",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest5() {
		assertTrue(secondPredicate(3, 4, 50));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,50);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	d's near false point
	@NearFalsePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f",
		implicant = "d",
		clause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest6() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	e's unique true point
	@UniqueTruePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f‌",
		implicant = "e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest7() {
		assertTrue(secondPredicate(30, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(30,4,5);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	e's near false point
	@NearFalsePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f",
		implicant = "e",
		clause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest8() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	f's unique true point
	@UniqueTruePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f‌",
		implicant = "f",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = true)
		}
	)
	@Test
	public void cutpnfpCoverTest9() {
		assertTrue(secondPredicate(3, 40, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,40,5);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2		-	f's near false point
	@NearFalsePoint(
		predicate = "d + e + f‌",
		dnf = "d + e + f",
		implicant = "f",
		clause = 'f',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void cutpnfpCoverTest10() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	//	-----------------------------------------------------------------------------------

	/**
	 * todo
	 * explain your answer here
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @return	f = abd + cde
	 * For implicant abd we have three unique true points: {TTFTF, TTFTT, TTTTF}
	 * 		-For clause a,
	 * 			we can pair unique true point TTFTF with near false point FTFTF
	 * 			we can pair unique true point TTFTT with near false point FTFTT
	 * 			we can pair unique true point TTTTF with near false point FTTTF
	 * 		-For clause b,
	 * 			we can pair unique true point TTFTF with near false point TFFTF
	 * 	  		we can pair unique true point TTFTT with near false point TFFTT
	 * 	  		we can pair unique true point TTTTF with near false point TFTTF
	 * 		-For clause d,
	 * 			we can pair unique true point TTFTF with near false point TTFFF
	 * 	  		we can pair unique true point TTFTT with near false point TTFFT
	 * 	  		we can pair unique true point TTTTF with near false point TTTFF
	 * For implicant cde we have three unique true points: {FFTTT, TFTTT, FTTTT}
	 * 		-For clause c,
	 * 	  		we can pair unique true point FFTTT with near false point FFFTT
  	 * 			we can pair unique true point TFTTT with near false point TFFTT
  	 * 			we can pair unique true point FTTTT with near false point FTFTT
  	 * 		-For clause d,
  	 * 			we can pair unique true point FFTTT with near false point FFTFT
  	 * 	  		we can pair unique true point TFTTT with near false point TFTFT
  	 * 	  		we can pair unique true point FTTTT with near false point FTTFT
  	 * 		-For clause e,
  	 * 			we can pair unique true point FFTTT with near false point FFTTF
  	 * 	  		we can pair unique true point TFTTT with near false point TFTTF
  	 * 	  		we can pair unique true point FTTTT with near false point FTTTF
	 *
	 *	-------------------------------------------------------------------------------------------------------------------
	 *	!f	= (!a + !b + !d).(!c + !d + !e)
	 *		= !a!c + !a!d + !a!e + !b!c + !b!d + !b!e + !d!c + !d!d + !d!e
	 * For implicant !a!c we have one unique true points: {FTFTT}
	 * For implicant !a!d we have one unique true points: {FTTFT}
	 * For implicant !a!e we have two unique true points: {FTTTF}
	 * For implicant !b!c we have one unique true points: {TFFTT}
	 * For implicant !b!d we have one unique true points: {TFTFT}
	 * For implicant !b!e we have two unique true points: {TFTTF}
	 * For implicant !d!c we have one unique true points: {TTFFT}
	 * For implicant !d!d we have one unique true points: {TTTFT}
	 * For implicant !d!e we have two unique true points: {TTTFF}
	 *
	 */

	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
		predicate = ((a && b && d) || (c && d && e));
		return predicate;
	}
}

