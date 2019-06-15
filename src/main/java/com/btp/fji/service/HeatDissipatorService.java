package com.btp.fji.service;

import com.btp.fji.util.Stencil;

public class HeatDissipatorService {

    private static final double DIRECT_CONST = 0.25 * Math.sqrt(2) / (Math.sqrt(2) + 1.0);
    private static final double DIAGONAL_CONST = 0.25 / (Math.sqrt(2) + 1.0);

    private int iterations;

    private Stencil cond;
    private Stencil current;
    private Stencil next;

    public HeatDissipatorService(double[][] heat, double[][] cond) {
        if (heat.length != cond.length || heat[0].length != cond[0].length) {
            System.err.println("Dimensions input files are required to be the same!");
            System.exit(1);
        }

        this.iterations = 0;

        this.cond = new Stencil(cond);
        this.current = new Stencil(heat);
        this.next = new Stencil(heat);

        this.cond.addEmptyRingAroundMatrix();
        this.current.addEmptyRingAroundMatrix();
        this.next.addEmptyRingAroundMatrix();

        this.cond.exchangeOuterColumns();
        this.current.exchangeOuterColumns();
        this.next.exchangeOuterColumns();
    }

    public void run(int maxIterations, boolean verbose) {
        System.out.println(this);

        for(int i = 0; i < maxIterations; i++) {
            next();

            if(verbose) {
                System.out.println(this);
            }
        }

        if(!verbose) {
            System.out.println(this);
        }
    }

    public void run(double minDifference, boolean verbose) {
        int i = 0;
        //System.out.println(this);
        next();

        while(maxTempDifference() > minDifference) {
            if(verbose) {
                System.out.println(this);
            }

            next();
            i++;
        }

        System.out.println("Finished after iteration: " + i);
        System.out.println(this);
    }

    private void next() {
        for (int i = 1; i < current.height() - 1; i++) {
            for (int j = 1; j < current.width() - 1; j++) {
                next.set(i, j, nextTemp(i, j));
            }
        }

        Stencil temp = current;
        current = next;
        next = temp;
        current.exchangeOuterColumns();
        iterations++;
    }

    private double nextTemp(int i, int j) {
        double w = cond.get(i, j);
        double restW = 1 - w;

        return current.get(i, j) * w +
            current.sumDirectCells(i, j) * restW * DIRECT_CONST +
            current.sumDiagonalCells(i, j) * restW * DIAGONAL_CONST;
    }

    private double maxTempDifference() {
        double maxDifference = 0;

        for (int i = 1; i < current.height() - 1; i++) {
            for (int j = 1; j < current.width() - 1; j++) {
                double difference = Math.abs(current.get(i, j) - next.get(i, j));

                if(difference > maxDifference) {
                    maxDifference = difference;
                }
            }
        }

        return maxDifference;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Iterations: ").append(iterations).append('\n');

        for (int i = 1; i < current.height() - 1; i++) {
            for (int j = 1; j < current.width() - 1; j++) {
                str.append(String.format("%6.2f  ", current.get(i, j)));
            }
            str.append('\n');
        }

        return str.toString();
    }

}
