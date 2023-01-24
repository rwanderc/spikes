package com.wandercosta.spikes;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpikeDetector {

    private final int window;
    private final double standardDeviation;
    private final double factor;

    public SpikeDetector(final int window,
                         final double standardDeviation,
                         final double factor) {
        this.window = window;
        this.standardDeviation = standardDeviation;
        this.factor = factor;
    }

    public Stream<Double> detect(final Stream<Double> stream) {

        final Supplier<Double> supplier;
        final Stream<Double> results = Stream.generate(supplier);


        stream.peek()


                Stream.of(1, 2, 3, 4, 5, 6)
                .
    }
}
