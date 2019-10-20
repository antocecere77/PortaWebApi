package com.portal.webapp.mapping;

public class Articolo {

    private String codart;
    private String descrizione;
    private String um;

    public String getCodart() {
        return codart;
    }

    public void setCodart(String codart) {
        this.codart = codart;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    @Override
    public String toString() {
        return "Articolo{" +
                "codart='" + codart + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", um='" + um + '\'' +
                '}';
    }
}