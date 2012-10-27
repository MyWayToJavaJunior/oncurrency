package me.vgv.oncurrency.provider.cbr;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Vasily Vasilkov (vgv@vgv.me)
 */
public class DoubleAdapterTest {

	@Test(groups = "unit")
	public void testUnmarshal() throws Exception {
		DoubleAdapter doubleAdapter = new DoubleAdapter();
		Assert.assertEquals(doubleAdapter.unmarshal("123,345"), 123.345);
	}

	@Test(groups = "unit")
	public void testMarshal() throws Exception {
		DoubleAdapter doubleAdapter = new DoubleAdapter();
		Assert.assertEquals(doubleAdapter.marshal(123.345), "123,345");
	}
}
