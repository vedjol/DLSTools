package com.veda.hrentgelt.dlstools.util;

public class ArbeitnehmerKey {
    public final String prnr;
    public final int abrj;
    public final int abrm;

    public ArbeitnehmerKey(String prnr, int abrj, int abrm) {
        this.prnr = prnr;
        this.abrj = abrj;
        this.abrm = abrm;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArbeitnehmerKey)
            return ((ArbeitnehmerKey) obj).prnr.equals(prnr) && ((ArbeitnehmerKey) obj).abrj == abrj;
        return false;
    }

    public boolean isNewerThan(ArbeitnehmerKey k2) {
        return abrj > k2.abrj || abrj == k2.abrj && abrm > k2.abrm;
    }

    @Override
    public String toString() {
        return "PRNR: " + prnr + "; ABRJ: " + abrj + "; ABRM: " + abrm;
    }
}