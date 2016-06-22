package br.com.wgbn.meuguia.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Lugar implements Parcelable {

    public Double   lat;
    public Double   lon;
    public String   nome;
    public String   historia;
    public String   imagem;
    public Date     fundacao;

    public Lugar(){ }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lat);
        dest.writeValue(this.lon);
        dest.writeString(this.nome);
        dest.writeString(this.historia);
        dest.writeString(this.imagem);
        dest.writeLong(this.fundacao != null ? this.fundacao.getTime() : -1);
    }

    protected Lugar(Parcel in) {
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.nome = in.readString();
        this.historia = in.readString();
        this.imagem = in.readString();
        long tmpFundacao = in.readLong();
        this.fundacao = tmpFundacao == -1 ? null : new Date(tmpFundacao);
    }

    public static final Parcelable.Creator<Lugar> CREATOR = new Parcelable.Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel source) {
            return new Lugar(source);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };
}
