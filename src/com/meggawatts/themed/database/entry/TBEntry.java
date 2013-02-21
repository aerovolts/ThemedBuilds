package com.meggawatts.themed.database.entry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TBEntry {

    private String date;
    private String player;
    private String build;
    private boolean supplemental;
    private String lot;

    public TBEntry(String player, String build, boolean optional, String lot) {
        setInfo(player, build, optional, lot);
    }

    public void setInfo(String p, String b, boolean o, String l) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        setDate(sdf.format(Calendar.getInstance().getTime()));
        setPlayer(p);
        setBuild(b);
        setSupplemental(o);
        setLot(l);
    }

    public void setDate(String e) {
        this.date = e;
    }

    public void setPlayer(String p) {
        this.player = p;
    }

    public void setBuild(String b) {
        this.build = b;
    }

    public void setSupplemental(boolean o) {
        this.supplemental = o;
    }

    public void setLot(String l) {
        this.lot = l;
    }

    public String getDate() {
        return date;
    }

    public String getPlayer() {
        return player;
    }

    public String getBuild() {
        return build;
    }

    public boolean getSupplemental() {
        return supplemental;
    }

    public String getLot() {
        return lot;
    }
}
