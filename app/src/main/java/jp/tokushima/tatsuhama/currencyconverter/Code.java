package jp.tokushima.tatsuhama.currencyconverter;

/**
 * 通貨単位の種類
 * Created by tatsuhama on 15/12/09.
 */
public enum Code {

    JPY("円"),
    USD("$");

    private String mUnit;

    private Code(String unit) {
        mUnit = unit;
    }

    public String getUnit() {
        return mUnit;
    }
}
