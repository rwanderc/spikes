package com.wandercosta.spikes.generator;

import java.util.function.Supplier;
import java.util.random.RandomGenerator;

public final class DataGenerator implements Supplier<Double> {

    private static final int DEFAULT_MIN_OUTLIER_MULTIPLIER = 5;
    private static final int DEFAULT_MAX_OUTLIER_MULTIPLIER = 10;

    private final double min;
    private final double max;
    private final long sleep;
    private final double spikeProbability;
    private final double stepSize;
    private final int minOutlierMultiplier;
    private final int maxOutlierMultiplier;
    private final RandomGenerator random;

    private double memory;

    public DataGenerator(final double startMemory,
                         final double min,
                         final double max,
                         final long sleep,
                         final double spikeProbability,
                         final double stepSize) {
        this(startMemory, min, max, sleep, spikeProbability, stepSize, DEFAULT_MIN_OUTLIER_MULTIPLIER,
                DEFAULT_MAX_OUTLIER_MULTIPLIER, RandomGenerator.getDefault());
    }

    public DataGenerator(final double startMemory,
                         final double min,
                         final double max,
                         final long sleep,
                         final double spikeProbability,
                         final double stepSize,
                         final int minOutlierMultiplier,
                         final int maxOutlierMultiplier,
                         final RandomGenerator random) {
        this.memory = startMemory;
        this.min = min;
        this.max = max;
        this.sleep = sleep;
        this.spikeProbability = Math.max(0, Math.min(1, spikeProbability));
        this.stepSize = stepSize;
        this.minOutlierMultiplier = minOutlierMultiplier;
        this.maxOutlierMultiplier = maxOutlierMultiplier;
        this.random = random;
    }

    @Override
    public Double get() {
        silentSleep(sleep);

        final boolean isOutlier = random.nextDouble() <= spikeProbability;

        if (isOutlier) {
            final boolean isSpike = random.nextBoolean();
            final double localMin;
            final double localMax;
            if (isSpike) {
                localMin = memory + (minOutlierMultiplier * stepSize);
                localMax = memory + (maxOutlierMultiplier * stepSize);
            } else {
                localMin = memory - (maxOutlierMultiplier * stepSize);
                localMax = memory - (minOutlierMultiplier * stepSize);
            }
            return random.nextDouble(localMin, localMax);
        } else {
            final double localMin = Math.max(memory - stepSize, min);
            final double localMax = Math.min(memory + stepSize, max);
            this.memory = random.nextDouble(localMin, localMax);
            return memory;
        }
    }

    private void silentSleep(final long sleep) {
        if (sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
