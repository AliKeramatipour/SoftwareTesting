package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.ClauseCoverage;
import com.github.mryf323.tractatus.ClauseDefinition;
import com.github.mryf323.tractatus.Valuation;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "Side1 <= 0")
@ClauseDefinition(clause = 'b', def = "Side2 <= 0")
@ClauseDefinition(clause = 'c', def = "Side3 <= 0")
@ClauseDefinition(clause = 'd', def = "Side1+Side2 <= Side3")
@ClauseDefinition(clause = 'e', def = "Side2+Side3 <= Side1")
@ClauseDefinition(clause = 'f', def = "Side1+Side3 <= Side2")
public class TriTypeCcCoverage {

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

	//	--------------------	CLAUSE COVERAGE -------------------------------------------

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	all true
	@ClauseCoverage(
		predicate = "a + b + c",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void ccCoverageTest() {
		assertTrue(firstPredicate(-1, -1, -1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(-1,-1,-1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	all false
	@ClauseCoverage(
		predicate = "a + b + c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void ccCoverageTest0() {
		assertFalse(firstPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	first clause false
	@ClauseCoverage(
		predicate = "d + e + f",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void ccCoverageTest1() {
		assertTrue(secondPredicate(3, 4, 50));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,50);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	second clause false
	@ClauseCoverage(
		predicate = "d + e + f",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
			@Valuation(clause = 'f', valuation = false)
		}
	)
	@Test
	public void ccCoverageTest2() {
		assertTrue(secondPredicate(30, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(30,4,5);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	third clause false
	@ClauseCoverage(
		predicate = "d + e + f",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = true)
		}
	)
	@Test
	public void ccCoverageTest3() {
		assertTrue(secondPredicate(3, 40, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,40,5);
		assertEquals(4, triangle);
	}

}
