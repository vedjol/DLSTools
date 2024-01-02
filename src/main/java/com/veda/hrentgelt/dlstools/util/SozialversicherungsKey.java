package com.veda.hrentgelt.dlstools.util;

public class SozialversicherungsKey {
    public final String prnr;
    public final int abrj;
    public final int abrm;
    public final int lfdn;

    public SozialversicherungsKey(String prnr, int abrj, int abrm, int lfdn) {
        this.prnr = prnr;
        this.abrj = abrj;
        this.abrm = abrm;
        this.lfdn = lfdn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SozialversicherungsKey)
            return ((SozialversicherungsKey) obj).prnr.equals(prnr) && ((SozialversicherungsKey) obj).abrj == abrj;
        return false;
    }

    public boolean isNewerThan(SozialversicherungsKey k2) {
        return abrj > k2.abrj || abrj == k2.abrj && abrm > k2.abrm || abrj == k2.abrj && abrm == k2.abrm && lfdn > k2.lfdn;
    }

    @Override
    public String toString() {
        return "PRNR: " + prnr + "; ABRJ: " + abrj + "; ABRM: " + abrm + "; LFDN: " + lfdn;
    }
}
