package com.rtenhove.eg.builder.inheritance;

/**
 * A subclass of the base class. This extends the builder so that it can used
 * to fluently create {@linkplain Subclass} instances just as you'd expect:
 * <pre>
 *   Subclass subclass = Subclass.builder()
 *                               .baseProp1("foo")
 *                               .subclassProp1("bar")
 *                               .build();
 * </pre>
 *
 * This subclass's builder is set up to enable further subclassing. As we shall
 * see, this requires extra declarations to enable this.
 *
 * @author Ron Ten-Hove
 */
public class Subclass extends Base {
    private final String subclassProp1;
    private final String subclassProp2;
    // ...

    /**
     * To enable subclassing of this builder, we require a type parameter, just
     * as the {@linkplain Base.Builder} did, for the same reasons. We pass the
     * concrete builder type up to the base class.
     *
     * @param <B> the concrete builder class in use.
     */
    public static abstract class Builder<B extends Builder<B>> extends Base.Builder<B> {
        private String subclassProp1;
        private String subclassProp2;
        // ...

        public B subclassProp1(String subclassProp1) {
            this.subclassProp1 = subclassProp1;
            return self();
        }

        public B subclassProp2(String subclassProp2) {
            this.subclassProp2 = subclassProp2;
            return self();
        }

        // ...

        public Subclass build() {
            return new Subclass(this);
        }

        protected Builder() {}
    }

    public static Builder<?> builder() {
        return new SubclassBuilder();
    }

    public String getSubclassProp1() {
        return subclassProp1;
    }

    public String getSubclassProp2() {
        return subclassProp2;
    }

    // ...

    /**
     * As we did with the {@linkplain Base.Builder}, we provide a single
     * concrete implementation so the compiler will have the right type info to
     * use. This is needed for each concrete, non-final subclass.
     */
    private static class SubclassBuilder extends Builder<SubclassBuilder> {
        @Override
        protected SubclassBuilder self() {
            return this;
        }
    }

    protected Subclass(Builder<?> builder) {
        super(builder);
        subclassProp1 = builder.subclassProp1;
        subclassProp2 = builder.subclassProp2;
        // ...
    }

}
