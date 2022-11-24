package searching_supp;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class SkyLinesTest
{

    public static void compareIntervals(List<Interval> res, List<Interval> expected){
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), res.get(i));
        }
    }

    @Test
    public void testSimple()
    {
        /**
         * Representation
         *            |---------|
         *            |         |
         *            |         |
         *    |-------|---|     |
         *    |    |--|---|--|  |
         *    |    |  |   |  |  |
         *    |----|--|---|--|--|
         */
        Interval[] array = new Interval[]{
                new Interval(2, 6, 3),
                new Interval(1, 4, 4),
                new Interval(3,7, 7)
        };

        Interval[] expected = new Interval[]{
                new Interval(1,3, 4),
                new Interval(3, 7, 7)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testSameHeight()
    {
        /**
         * Representation
         *    |-------|---|-----|
         *    |       |   |     |
         *    |       |   |     |
         *    |-------|---|-----|
         */
        Interval[] array = new Interval[]{
                new Interval(2, 4, 3),
                new Interval(3, 7, 3),
        };

        Interval[] expected = new Interval[]{
                new Interval(2,7, 3)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testCornerCase()
    {
        /**
         * Representation
         *    |-------|--------|
         *    |       |        |
         *    |       |        |
         *    |-------|--------|
         */
        Interval[] array = new Interval[]{
                new Interval(2, 4, 3),
                new Interval(4, 7, 3),
        };

        Interval[] expected = new Interval[]{
                new Interval(2,7, 3)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testCornerCase2()
    {
        /**
         * Representation
         *    |-------|--------|
         *    |       |        |
         *    |       |        |
         *    |-------|--------|
         */
        Interval[] array = new Interval[]{
                new Interval(1, 5, 3),
                new Interval(1, 10, 3),
        };

        Interval[] expected = new Interval[]{
                new Interval(1,10, 3)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testCornerCase3()
    {
        /**
         * Representation
         *    |-------|--------|
         *    |       |        |
         *    |       |        |
         *    |-------|--------|
         */
        Interval[] array = new Interval[]{
                new Interval(1, 10, 3),
                new Interval(4, 6, 3),
        };

        Interval[] expected = new Interval[]{
                new Interval(1,10, 3)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testReduce()
    {
        /**
         * Representation
         *    |-------|
         *    |       |--------|
         *    |       |        |
         *    |-------|--------|
         */
        Interval[] array = new Interval[]{
                new Interval(2, 4, 3),
                new Interval(4, 7, 2)
        };

        Interval[] expected = new Interval[]{
                new Interval(2,4, 3),
                new Interval(4, 7, 2)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }

    @Test
    public void testMultipleBuildings()
    {
        /**
         * Representation
         *            |---------|
         *            |         |
         *            |         |
         *    |-------|---|     |   |-----|
         *    |    |--|---|--|  |   |   |-|---|
         *    |    |  |   |  |  |   |   | |   |
         *    |----|--|---|--|--|   |---|-|---|
         */
        Interval[] array = new Interval[]{
                new Interval(2, 6, 3),
                new Interval(1, 4, 4),
                new Interval(3,7, 7),
                new Interval(8, 10, 3),
                new Interval(9, 13, 2)
        };

        Interval[] expected = new Interval[]{
                new Interval(1,3, 4),
                new Interval(3, 7, 7),
                new Interval(8,10, 3),
                new Interval(10, 13, 2)
        };

        List<Interval> ret = SkyLines.horizon(Arrays.asList(array));
        assertNotNull(ret);
        assertEquals(expected.length, ret.size());
        compareIntervals(ret, Arrays.asList(expected));
    }
}
