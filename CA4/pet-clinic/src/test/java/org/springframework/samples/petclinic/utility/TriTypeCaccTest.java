package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.CACC;
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
public class TriTypeCaccTest {

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

	//	--------------------	CACC COVERAGE ---------------------------------------------

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	first clause is the active clause and it's true
	@CACC(
		predicate ="a + b + c",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest() {
		assertTrue(firstPredicate(-1, 1, 1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(-1,1,1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	first clause is the active clause and it's false
	@CACC(
		predicate ="a + b + c",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest0() {
		assertFalse(firstPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	second clause is the active clause and it's true
	@CACC(
		predicate ="a + b + c",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest1() {
		assertTrue(firstPredicate(1, -1, 1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(1,-1,1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	second clause is the active clause and it's false
	@CACC(
		predicate ="a + b + c",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest2() {
		assertFalse(firstPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	third clause is the active clause and it's true
	@CACC(
		predicate ="a + b + c",
		majorClause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest3() {
		assertTrue(firstPredicate(1, 1, -1));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(1,1,-1);
		assertEquals(4, triangle);
	}

	// predicate = Side1 <= 0 || Side2 <= 0 || Side3 <= 0	-	third clause is the active clause and it's false
	@CACC(
		predicate ="a + b + c",
		majorClause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest4() {
		assertFalse(firstPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	first clause is the active clause and it's true
	@CACC(
		predicate ="d + e + f",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest5() {
		assertTrue(secondPredicate(3, 4, 50));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,50);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	first clause is the active clause and it's false
	@CACC(
		predicate ="d + e + f",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest6() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	second clause is the active clause and it's true
	@CACC(
		predicate ="d + e + f",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
			@Valuation(clause = 'f', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest7() {
		assertTrue(secondPredicate(30, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(30,4,5);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	second clause is the active clause and it's false
	@CACC(
		predicate ="d + e + f",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest8() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	third clause is the active clause and it's true
	@CACC(
		predicate ="d + e + f",
		majorClause = 'f',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = true)
		},
		predicateValue = true
	)
	@Test
	public void caccCoverTest9() {
		assertTrue(secondPredicate(3, 40, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,40,5);
		assertEquals(4, triangle);
	}

	// Side1+Side2 <= Side3 || Side2+Side3 <= Side1 || Side1+Side3 <= Side2	-	third clause is the active clause and it's false
	@CACC(
		predicate ="d + e + f",
		majorClause = 'f',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
			@Valuation(clause = 'f', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void caccCoverTest10() {
		assertFalse(secondPredicate(3, 4, 5));
		TriType tryType = new TriType();
		int triangle;
		triangle = tryType.recognizeTriangleByCode(3,4,5);
		assertEquals(1, triangle);
	}
}

