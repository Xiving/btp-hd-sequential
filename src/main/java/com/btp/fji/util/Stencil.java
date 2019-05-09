package com.btp.fji.util;

public class Stencil {

    private double[][] stencil;

    public Stencil(double[][] stencil) {
        this.stencil = stencil;
    }

    public int width() {
        return stencil[0].length;
    }

    public int height() {
        return stencil.length;
    }

    public double get(int i, int j) {
        return stencil[i][j];
    }

    public void set(int i, int j, double v) {
        stencil[i][j] = v;
    }

    public void addEmptyRingAroundMatrix() {
        double[][] donut = new double[stencil.length + 2][stencil[0].length + 2];

        for (int i = 0; i < donut.length - 2; i++) {
            for (int j = 0; j < donut[i].length - 2; j++) {
                donut[i + 1][j + 1] = stencil[i][j];
            }
        }

        stencil = donut;
    }

    public void exchangeOuterColumns() {
        if (stencil.length < 4) {
            return;
        }

        for (int i = 0; i < stencil.length; i++) {
            stencil[i][0] = stencil[i][stencil[i].length - 2];
            stencil[i][stencil[i].length - 1] = stencil[i][1];
        }
    }

    public double sumDirectCells(int i, int j) {
        return stencil[i + 1][j] + stencil[i - 1][j] +
            stencil[i][j + 1] + stencil[i][j - 1];
    }

    public double sumDiagonalCells(int i, int j) {
        return stencil[i + 1][j + 1] + stencil[i - 1][j + 1] +
                stencil[i + 1][j - 1] + stencil[i - 1][j - 1];
    }

}
