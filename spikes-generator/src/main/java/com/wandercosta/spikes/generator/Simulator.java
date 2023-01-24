package com.wandercosta.spikes.generator;

import java.util.stream.Stream;

public final class Simulator {

    private final DataGenerator dataGenerator;

    public Simulator(final DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    public Stream<Double> generate() {
        return Stream.generate(dataGenerator);
    }

    public static void main(final String[] args) {
        final DataGenerator supplier = new DataGenerator(0, -10d, 20d, 1L, 0.005d, 0.2d);
        final Simulator simulator = new Simulator(supplier);
        simulator.generate().forEach(d -> {

            System.out.println(d);
        });
    }
}
