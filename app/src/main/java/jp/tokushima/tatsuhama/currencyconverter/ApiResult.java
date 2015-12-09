package jp.tokushima.tatsuhama.currencyconverter;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tatsuhama on 15/12/09.
 */
public class ApiResult extends RealmObject{

    @PrimaryKey
    private long requestTime;
    private String fromCode;
    private String toCode;
    private double factor;

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }
}
