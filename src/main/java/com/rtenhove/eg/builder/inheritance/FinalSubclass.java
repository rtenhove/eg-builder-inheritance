package com.rtenhove.eg.builder.inheritance;

/**
 * A {@code final} subclass of our subclass. Since it is final, we can avoid
 * most of the ceremonies the non-final classes had to perform to support
 * fluent builders.
 *
 * @author Ron Ten-Hove
 */
public class FinalSubclass extends Subclass {
    private final String finalProp1;
    private final String finalProp2;
    // ...

    /**
     * This builder declaration differs from the {@linkplain Subclass.Builder}
     * in the following ways:
     * <ul>
     *     <li>
     *         It is not abstract (it directly implements the self() method,
     *         since it doesn't need to worry about allows subclasses to
     *         support the "getThis()" trick. Of course, this means we don't
     *         need to provide a (separate) concrete implementation builder
     *         class.
     *     </li>
     *     <li>
     *         No new type parameter is declared, since we don't need to thread
     *         through builder subclass type information. Instead, we just use
     *         this builder class as the type to pass up to the super class.
     *     </li>
     * </ul>
     */
    public static final class Builder extends Subclass.Builder<Builder> {
        private String finalProp1;
        private String finalProp2;
        // ...

        public Builder finalProp1(String finalProp1) {
            this.finalProp1 = finalProp1;
            return self();
        }

        public Builder finalProp2(String finalProp2) {
            this.finalProp2 = finalProp2;
            return self();
        }

        // ...

        public FinalSubclass build() {
            return new FinalSubclass(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getFinalProp1() {
        return finalProp1;
    }

    public String getFinalProp2() {
        return finalProp2;
    }

    // ...

    private FinalSubclass(Builder builder) {
        super(builder);
        this.finalProp1 = builder.finalProp1;
        this.finalProp2 = builder.finalProp2;
        // ...
    }

}
