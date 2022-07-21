package io.mvvm.wechat.mp.infra;

import lombok.Data;

/**
 * @description: 两个对象
 * @author: Pan
 **/
@Data
public class Pair<F, S> implements Cloneable {

    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> create(F first, S second) {
        return new Pair<>(first, second);
    }

    @Override
    public Pair<F, S> clone() throws CloneNotSupportedException {
        super.clone();
        return new Pair<>(getFirst(), getSecond());
    }
}
