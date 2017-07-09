package org.chembar.glockchem.core;

import java.io.Serializable;

/**
 * Pair
 *
 * @param <L> Pair左侧类型
 * @param <R> Pair右侧类型
 * @author DuckSoft
 */
public class Pair<L, R> implements Serializable {
    private L l;
    private R r;

    public String toString() {
        return "( " + l.toString() + "," + r.toString() + ") ";
    }

    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public L getL() {
        return l;
    }

    public R getR() {
        return r;
    }

    public void setL(L l) {
        this.l = l;
    }

    public void setR(R r) {
        this.r = r;
    }
}