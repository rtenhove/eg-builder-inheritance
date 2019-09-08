package com.rtenhove.eg.builder.inheritance.test;

import com.rtenhove.eg.builder.inheritance.Base;
import com.rtenhove.eg.builder.inheritance.FinalSubclass;
import com.rtenhove.eg.builder.inheritance.Subclass;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Just some simple tests to prove that things work as expected.
 *
 * @author Ron Ten-Hove
 */
class BuilderTest {
    @Test
    void testBaseBuilder() {
        String expectedBaseProp1 = getRandomString();
        String expectedBaseProp2 = getRandomString();

        Base base = Base.builder()
                .baseProp1(expectedBaseProp1)
                .baseProp2(expectedBaseProp2)
                .build();

        assertThat(base, is(notNullValue()));
        assertThat(base.getBaseProp1(), is(expectedBaseProp1));
        assertThat(base.getBaseProp2(), is(expectedBaseProp2));
    }

    @Test
    void testSubclassBuilder() {
        String expectedBaseProp1 = getRandomString();
        String expectedBaseProp2 = getRandomString();
        String expectedSubclassProp1 = getRandomString();
        String expectedSubclassProp2 = getRandomString();

        Subclass subclass = Subclass.builder()
                .baseProp1(expectedBaseProp1)
                .baseProp2(expectedBaseProp2)
                .subclassProp1(expectedSubclassProp1)
                .subclassProp2(expectedSubclassProp2)
                .build();

        assertThat(subclass, is(notNullValue()));
        assertThat(subclass.getBaseProp1(), is(expectedBaseProp1));
        assertThat(subclass.getBaseProp2(), is(expectedBaseProp2));
        assertThat(subclass.getSubclassProp1(), is(expectedSubclassProp1));
        assertThat(subclass.getSubclassProp2(), is(expectedSubclassProp2));
    }

    @Test
    void testFinalSubclassBuilder() {
        String expectedBaseProp1 = getRandomString();
        String expectedBaseProp2 = getRandomString();
        String expectedSubclassProp1 = getRandomString();
        String expectedSubclassProp2 = getRandomString();
        String expectedFinalSubclassProp1 = getRandomString();
        String expectedFinalSubclassProp2 = getRandomString();

        FinalSubclass finalSubclass = FinalSubclass.builder()
                .baseProp1(expectedBaseProp1)
                .baseProp2(expectedBaseProp2)
                .subclassProp1(expectedSubclassProp1)
                .subclassProp2(expectedSubclassProp2)
                .finalProp1(expectedFinalSubclassProp1)
                .finalProp2(expectedFinalSubclassProp2)
                .build();

        assertThat(finalSubclass, is(notNullValue()));
        assertThat(finalSubclass.getBaseProp1(), is(expectedBaseProp1));
        assertThat(finalSubclass.getBaseProp2(), is(expectedBaseProp2));
        assertThat(finalSubclass.getSubclassProp1(), is(expectedSubclassProp1));
        assertThat(finalSubclass.getSubclassProp2(), is(expectedSubclassProp2));
        assertThat(finalSubclass.getFinalProp1(), is(expectedFinalSubclassProp1));
        assertThat(finalSubclass.getFinalProp2(), is(expectedFinalSubclassProp2));
    }

    private static String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
