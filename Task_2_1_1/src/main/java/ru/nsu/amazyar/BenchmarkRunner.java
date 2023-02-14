package ru.nsu.amazyar;

import java.util.ArrayList;
import java.util.List;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
        for (int i = 0; i < 10; i++){
            longerNonPrimesList.addAll(longNonPrimesList);
        }
    }

    @Param({"1", "2", "3", "4"})
    public int threadsCount;

    //-------Big list-------
    @Benchmark
    public boolean multiThreadingBigBenchmark(){
        return multithreadingPrimeFinder.containsNoPrimes(bigNonPrimesList, threadsCount);
    }

    @Benchmark
    public boolean parallelStreamBigBenchmark(){
        return parallelStreamPrimeFinder.containsNoPrimes(bigNonPrimesList);
    }

    @Benchmark
    public boolean SequentialBigBenchmark(){
        return primeFinder.containsNoPrimes(bigNonPrimesList);
    }

    //-------Long list-------
    @Benchmark
    public boolean multiThreadingLongBenchmark(){
        return multithreadingPrimeFinder.containsNoPrimes(longNonPrimesList, threadsCount);
    }

    @Benchmark
    public boolean parallelStreamLongBenchmark(){
        return parallelStreamPrimeFinder.containsNoPrimes(longNonPrimesList);
    }

    @Benchmark
    public boolean SequentialLongBenchmark(){
        return primeFinder.containsNoPrimes(longNonPrimesList);
    }

    //-------Longer list-------
    @Benchmark
    public boolean multiThreadingLongerBenchmark(){
        return multithreadingPrimeFinder.containsNoPrimes(longerNonPrimesList, threadsCount);
    }

    @Benchmark
    public boolean parallelStreamLongerBenchmark(){
        return parallelStreamPrimeFinder.containsNoPrimes(longerNonPrimesList);
    }

    @Benchmark
    public boolean SequentialLongerBenchmark(){
        return primeFinder.containsNoPrimes(longerNonPrimesList);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BenchmarkRunner.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }

}
