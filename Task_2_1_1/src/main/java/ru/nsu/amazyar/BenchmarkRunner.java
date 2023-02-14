package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Runs benchmark.
 */
@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Benchmark)
public class BenchmarkRunner {

    static PrimeFinder primeFinder = new PrimeFinder();
    static MultithreadingPrimeFinder multithreadingPrimeFinder = new MultithreadingPrimeFinder();
    static ParallelStreamPrimeFinder parallelStreamPrimeFinder = new ParallelStreamPrimeFinder();
    static List<Integer> bigNonPrimesList;
    static List<Integer> longNonPrimesList;
    static List<Integer> longerNonPrimesList;

    /**
     * Initialise data for benchmarking.
     */
    @Setup
    public static void listsInit() {

        bigNonPrimesList = new ArrayList<>();
        for (int i = 3; i <= 45340; i += 2) {
            bigNonPrimesList.add(i * (i + 2));
        }

        longNonPrimesList = new ArrayList<>();
        for (int i = 3; i <= 45340; i += 2) {
            for (int j = 2; j <= 1024; j += 2) {
                longNonPrimesList.add(i * (i + j));
            }
        }

        longerNonPrimesList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            longerNonPrimesList.addAll(longNonPrimesList);
        }
    }

    @Param({"1", "2", "3", "4"})
    public int threadsCount;

    //-------Big list-------
    /**
     * Run big list with multithreadingPrimeFinder.
     */
    @Benchmark
    public boolean multiThreadingBigBenchmark() {
        return multithreadingPrimeFinder.containsNoPrimes(bigNonPrimesList, threadsCount);
    }

    /**
     * Run big list with parallelStreamPrimeFinder.
     */
    @Benchmark
    public boolean parallelStreamBigBenchmark() {
        return parallelStreamPrimeFinder.containsNoPrimes(bigNonPrimesList);
    }

    /**
     * Run big list with simple primeFinder.
     */
    @Benchmark
    public boolean SequentialBigBenchmark() {
        return primeFinder.containsNoPrimes(bigNonPrimesList);
    }

    //-------Long list-------
    /**
     * Run long list with multithreadingPrimeFinder.
     */
    @Benchmark
    public boolean multiThreadingLongBenchmark() {
        return multithreadingPrimeFinder.containsNoPrimes(longNonPrimesList, threadsCount);
    }

    /**
     * Run long list with parallelStreamPrimeFinder.
     */
    @Benchmark
    public boolean parallelStreamLongBenchmark() {
        return parallelStreamPrimeFinder.containsNoPrimes(longNonPrimesList);
    }

    /**
     * Run long list with simple primeFinder.
     */
    @Benchmark
    public boolean SequentialLongBenchmark() {
        return primeFinder.containsNoPrimes(longNonPrimesList);
    }

    //-------Longer list-------
    /**
     * Run longer list with multithreadingPrimeFinder.
     */
    @Benchmark
    public boolean multiThreadingLongerBenchmark() {
        return multithreadingPrimeFinder.containsNoPrimes(longerNonPrimesList, threadsCount);
    }

    /**
     * Run longer list with parallelStreamPrimeFinder.
     */
    @Benchmark
    public boolean parallelStreamLongerBenchmark() {
        return parallelStreamPrimeFinder.containsNoPrimes(longerNonPrimesList);
    }

    /**
     * Run longer list with simple primeFinder.
     */
    @Benchmark
    public boolean SequentialLongerBenchmark() {
        return primeFinder.containsNoPrimes(longerNonPrimesList);
    }

    /**
     * Executes benchmarking.
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BenchmarkRunner.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }

}
