package edu.illinois.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by dan5082 on 11/22/2017.
 */

public class Coin implements Parcelable {


    /**
     *  "id": "bitcoin",
     "name": "Bitcoin",
     "symbol": "BTC",
     "rank": "1",
     "price_usd": "8150.65",
     "price_btc": "1.0",
     "24h_volume_usd": "3664120000.0",
     "market_cap_usd": "136076829688",
     "available_supply": "16695212.0",
     "total_supply": "16695212.0",
     "max_supply": "21000000.0",
     "percent_change_1h": "0.16",
     "percent_change_24h": "-0.8",
     "percent_change_7d": "12.39",
     "last_updated": "1511378651"
     */

    String id;
    String name;
    String symbol;
    int rank;
    double price_usd;
    double price_btc;
    @SerializedName("24h_volume_usd")
    double tw4h_volume_usd;
    long market_cap_usd;
    double available_supply;
    double total_supply;

    public String getId() {
        return id;
    }

    public String getImageUrl(){
        String baseUrl = "https://files.coinmarketcap.com/static/img/coins/32x32/";
        String url = baseUrl + id;
        return url + ".png";
    }


    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getRank() {
        return rank;
    }

    public double getPrice_usd() {
        return price_usd;
    }

    public double getPrice_btc() {
        return price_btc;
    }

    public double getTw4h_volume_usd() {
        return tw4h_volume_usd;
    }

    public long getMarket_cap_usd() {
        return market_cap_usd;
    }

    public double getAvailable_supply() {
        return available_supply;
    }

    public double getTotal_supply() {
        return total_supply;
    }

    public double getMax_supply() {
        return max_supply;
    }

    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public long getLast_updated() {
        return last_updated;
    }

    double max_supply;
    double percent_change_1h;
    double percent_change_24h;
    double percent_change_7d;
    long last_updated;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.symbol);
        dest.writeInt(this.rank);
        dest.writeDouble(this.price_usd);
        dest.writeDouble(this.price_btc);
        dest.writeDouble(this.tw4h_volume_usd);
        dest.writeLong(this.market_cap_usd);
        dest.writeDouble(this.available_supply);
        dest.writeDouble(this.total_supply);
        dest.writeDouble(this.max_supply);
        dest.writeDouble(this.percent_change_1h);
        dest.writeDouble(this.percent_change_24h);
        dest.writeDouble(this.percent_change_7d);
        dest.writeLong(this.last_updated);
    }

    public Coin() {
    }

    protected Coin(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.symbol = in.readString();
        this.rank = in.readInt();
        this.price_usd = in.readDouble();
        this.price_btc = in.readDouble();
        this.tw4h_volume_usd = in.readDouble();
        this.market_cap_usd = in.readLong();
        this.available_supply = in.readDouble();
        this.total_supply = in.readDouble();
        this.max_supply = in.readDouble();
        this.percent_change_1h = in.readDouble();
        this.percent_change_24h = in.readDouble();
        this.percent_change_7d = in.readDouble();
        this.last_updated = in.readLong();
    }

    public static final Parcelable.Creator<Coin> CREATOR = new Parcelable.Creator<Coin>() {
        @Override
        public Coin createFromParcel(Parcel source) {
            return new Coin(source);
        }

        @Override
        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };
}
