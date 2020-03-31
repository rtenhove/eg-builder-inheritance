Java Builder Pattern with Inheritance (and no ugly casts!)
=
This very small example project details how to support the builder pattern for a Java class hierarchy,
by utilizing Java generics.

The Problem
==
The builder pattern is useful for building objects that have many properties,
avoiding the the need for big, ugly constructors, or large numbers of
constructors, or both. [EJ 2]

```java
public class Rocket {
    private final String name;
    private final int stages;
    // ... and many more properties
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String name;
        private int stages;
        // ... and many more properties
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder stages(int stages) {
            this.stages = stages;
            return this;
        }
        
        // ... and many more fluent setters for the other properties
        
        public Rocket build() {
            return new Rocket(this);
        }
        
        protected Builder() {}
    }
    
    protected Rocket(Builder builder) {
        name = builder.name;
        stages = builder.stages;
    }
}
```
which allows us to fluently build `Rocket` instances:
```java 
Rocket rocket = Rocket.builder()
                      .name("Falcon 1")
                      .stages(2)
                      .build();
``` 

In Java this works well for situations where inheritance is not involved.
But suppose we want to specialize `Rocket`? A naive approach might look
something like:
```java
public class AirLaunchRocket extends Rocket {
    private final String carrierAircraftName;
    // ...
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder extends Rocket.Builder {
        private String carrierAircraftName;
        // ...
        
        public Builder carrierAircraftName(String carrierAircraftName) {
            this.carrierAircraftName = carrierAircraftName;
            return this;
        }
        
        public AirLaunchRocket build() {
            return new AirLaunchRocket(builder());
        }
        
        protected Builder() {}
    }
    
    protected AirLaunchRocket(Builder builder) {
        super(builder);
        carrierAircraftName = builder.carrierAircraftName;
    }
}
```
Looks like a pleasing parallel specialization pattern, for the domain
class and its builder class. But it doesn't work:
```java
AirLaunchRocket rocket = 
    AirLaunchRocket.builder()
                   .name("SpaceShipTwo")
                   .stages(1)
                   .carrierAircraftName("White Knight Two") // Method not found compile error here!
                   .build();
```
The problem is the fluent setter methods for the base `Rocket` builder class. They all return 
`Rocket.Builder`, causing the compiler to rightly complain that `Rocket.Builder` doesn't have a 
`carrierAircraftName(String)` method. If we get clever, and order the setters to involve the subclass
properties first, we just shift the compile error:
```java
AirLaunchRocket rocket = 
    AirLaunchRocket.builder()
                   .carrierAircraftName("White Knight Two")
                   .name("SpaceShipTwo")
                   .stages(1)
                   .build();   // Type mismatch compile error here!
```
The `build()` method to be called is from `Rocket.Builder`, returning a `Rocket`, rather than an 
`AirLaunchRocket` as desired.

The Solution
==
This problem can be fixed, but it means taking a journey into the world of Java generics. This is not
as straightforward as one would wish, so I'm using this tiny example project to give a detailed blueprint
for a working solution that avoids bad code smells, such as casts. If you don't mind such casts, the
solution can be somewhat simplified.

The code details how to approach three separate cases:
* The base class whose builder supports subclassing
* Subclasses that also support being subclassed
* `final` subclasses, whose builders can be implemented more simply

Enough talk... check out the code! The comments should make things clear.
