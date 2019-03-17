package guava;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BloomFilterUnitTest {

    /**
     * 给定一个整数布隆过滤器，容量500，出错概率0.01
     * 输入1，2，3
     * 测试1，2，3，100
     */
    @Test
    public void givenBloomFilter_whenAddNStringsToIt_thenShouldNotReturnAnyFalsePositive() {
        /**
         * 500
         * 0.01
         */
        //when
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                500,
                0.01);

        //when
        filter.put(1);
        filter.put(2);
        filter.put(3);

        //then
        // the probability that it returns true, but is actually false is 1%
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();

        assertThat(filter.mightContain(100)).isFalse();
    }

    /**
     * 给定一个整数布隆过滤器，容量5，出错概率0.01
     * 输入0-100_000
     * 测试1，2，3，100
     * 样本多，容量少时:错误率很高
     */
    @Test
    public void givenBloomFilter_whenAddNStringsToItMoreThanDefinedExpectedInsertions_thenItWillReturnTrueForAlmostAllElements() {
        //when
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                5,
                0.01);

        //when
        IntStream.range(0, 100000).forEach(filter::put);


        //then
        assertThat(filter.mightContain(1)).isTrue();
        assertThat(filter.mightContain(2)).isTrue();
        assertThat(filter.mightContain(3)).isTrue();
        assertThat(filter.mightContain(1000000000)).isTrue();
    }
}
