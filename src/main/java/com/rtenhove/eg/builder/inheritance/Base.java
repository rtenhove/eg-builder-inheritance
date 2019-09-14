package com.rtenhove.eg.builder.inheritance;

/**
 * A base class with an embedded builder, set up for subclassing in such a
 * fashion that subclass builders work as expected. Warning: Java generics are
 * involved, which is always more complicated than I'd like, due to type
 * erasure.
 *
 * @author Ron Ten-Hove
 */
public class Base {
    private final String baseProp1;
    private final String baseProp2;
    // ...

    /**
     * The builder requires a generic type parameter {@code <B>}, which is the
     * type returned by the builder's fluent setter methods. This assures that
     * the Java compiler has correct concrete builder type information, even
     * when the {@linkplain Base.Builder} setter methods are invoked on a sub-
     * classed builder.
     * <p>
     * The constraint on the generic type may look odd to you, because it is
     * self-referential. If you think about it is the way of declaring that the
     * type must be a subclass of this builder (or the {@linkplain Builder}
     * class itself, of course).
     * </p>
     *
     * @param <B> the concrete builder class in use.
     */
    public static abstract class Builder<B extends Builder<B>> {
        private String baseProp1;
        private String baseProp2;
        // ...

        public B baseProp1(String baseProp1) {
            this.baseProp1 = baseProp1;
            return self();
        }

        public B baseProp2(String baseProp2) {
            this.baseProp2 = baseProp2;
            return self();
        }

        // ...

        public Base build() {
            return new Base(this);
        }

        /**
         * This method must be implemented by the concrete builder classes.
         * This is an instance of the <a
         * href="http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#FAQ206">
         * getThis() trick</a>, much beloved by Java generic-type gurus.
         * <p>
         * We must have only one implementation of this method in the class
         * hierarchy, otherwise the compiler will not be able to track the type
         * information, since more than one possible concrete type is possible.
         * See the {@linkplain Base.BaseBuilder} class for how we ensure that
         * this does not occur.
         * </p>
         * <p><br>
         * Why this complication? I want to avoid needing to cast the return
         * type of the builder's fluent setter methods:
         * <pre>
         *   public B baseProp1(String baseProp1) {
         *       this.baseProp1 = baseProp1;
         *       return (B) this;
         *   }
         * </pre>
         * I don't like down-casts, because they blinds the compiler, putting
         * you at risk of introducing defects that won't be apparent until
         * run-time, perhaps only under unusual circumstances. Others argue
         * that if you can provide solid reasoning for being assured that the
         * cast is safe (such as the self-referential type constraint declared
         * by the {@linkplain Base.Builder}'s declaration of {@code <B>}) you
         * can safely cast, and avoid this extra bit of ceremony (which
         * includes {@linkplain Base.BaseBuilder} to complete
         * the "getThis" pattern.
         * </p>
         *
         * @return concrete builder {@code} this
         */
        protected abstract B self();

        protected Builder() {}
    }

    public static Builder<?> builder() {
        return new BaseBuilder();
    }

    public String getBaseProp1() {
        return baseProp1;
    }

    public String getBaseProp2() {
        return baseProp2;
    }

    // ...

    /**
     * The concrete builder for {@linkplain Base} objects. This completes the
     * "getThis" trick mentioned in the {@linkplain Builder#self()} JavaDoc.
     * Each non-abstract subclass of the base class must perform this
     * ceremony.
     * <p>
     * This gets more interesting in the sub-classes.
     * </p>
     */
    private static class BaseBuilder extends Builder<BaseBuilder> {
        @Override
        protected BaseBuilder self() {
            return this;
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected Base(Builder<?> builder) {
        baseProp1 = builder.baseProp1;
        baseProp2 = builder.baseProp2;
        // ...
    }
}
