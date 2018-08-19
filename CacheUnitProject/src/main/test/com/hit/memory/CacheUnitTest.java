package com.hit.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

class CacheUnitTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCacheUnit() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDataModels() {

		File f = new File("myfile.txt");
		if (f.exists()) {
		
		} else {
			System.out.println("No Existing Records");
		}

		final long id1 = 1L;

		final long id2 = 2L;
		final long id3 = 3L;
		final long id4 = 4L;
		final long id5 = 5L;

		DataModel<String> dm = new DataModel<String>(id1, "a");
		DataModel<String> dm2 = new DataModel<String>(id2, "b");
		DataModel<String> dm3 = new DataModel<String>(id3, "c");
		DataModel<String> dm4 = new DataModel<String>(id4, "d");
		DataModel<String> dm5 = new DataModel<String>(id5, "e");
		CacheUnit tst = null;
		IAlgoCache<java.lang.Long, DataModel<String>> algo = new LRUAlgoCacheImpl(2);
		IDao<java.io.Serializable, DataModel<String>> dao = null;

		dao = new DaoFileImpl("myfile.txt");

		dao.save(dm);
		dao.save(dm2);
		dao.save(dm3);
		dao.save(dm4);
		dao.save(dm5);

		dao.delete(dm2);
		tst = new CacheUnit(algo, dao);
		Long[] ids = { id1, id2, id3, id4, id5 };
		DataModel<String>[] a = tst.getDataModels(ids);

		assertEquals("a", a[0].getContent().toString());
		assertEquals(null, a[1]);
		assertEquals("c", a[2].getContent().toString());
		assertEquals("d", a[3].getContent().toString());
		assertEquals("e", a[4].getContent().toString());

	}

}
