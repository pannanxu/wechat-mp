package io.mvvm.wechat.mp.infra;

import lombok.Getter;

/**
 * @program: wechat-mp
 * @description: 参数包装器
 * @author: Pan
 * @create: 2022-07-16 21:33
 **/
public class ValueWrappers {

    public static <T1, T2> Value2<T1, T2> of(T1 t1, T2 t2) {
        return new Value2<>(t1, t2);
    }

    public static <T1, T2, T3> Value3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Value3<>(t1, t2, t3);
    }

    public static <T1, T2, T3, T4> Value4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Value4<>(t1, t2, t3, t4);
    }

    @Getter
    public static class Value2<T1, T2> {
        private final T1 t1;
        private final T2 t2;

        public Value2(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
        }
    }

    @Getter
    public static class Value3<T1, T2, T3> extends Value2<T1, T2> {
        private final T3 t3;

        public Value3(T1 t1, T2 t2, T3 t3) {
            super(t1, t2);
            this.t3 = t3;
        }
    }

    @Getter
    public static class Value4<T1, T2, T3, T4> extends Value3<T1, T2, T3> {
        private final T4 t4;

        public Value4(T1 t1, T2 t2, T3 t3, T4 t4) {
            super(t1, t2, t3);
            this.t4 = t4;
        }
    }

}
