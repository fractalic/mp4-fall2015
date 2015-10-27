CPEN 221 / Fall 2015: Machine Problem 4 
Interfaces, Subtypes, and Virtual Worlds
====

In this machine problem you will complete and extend a virtual world. This world contains many different items -- animate and inanimate -- which can interact with each other in complex ways. For example, Foxes hunt for Rabbits, which in turn try to outwit their predators. It's up to you to determine just how complex the interaction will be.

Your goals for this machine problem are to:
+ Practice encapsulation and code reuse with subtypes and delegation;
+ Program against existing interfaces;
+ Think about code from a design point of view.

### Overview

We have developed a virtual-world environment that can simulate the interaction of many items and actors. This world is flat and consists of many fields that can each have one `Item`. In the beginning the world will contain `Grass`, `Rabbit`s, and `Fox`es, and you will add additional `Item`s as part of this assignment. 

Time in the virtual-world simulation progresses in discrete steps; in every step an `Actor` may act, for example, by moving,
eating, or breeding. We have provided some simple AIs for rabbits and foxes; you will implement more intelligent AIs for them and other items you create.

This assignment has two parts: 
+ filling the world with additional items, 
+ and creating intelligent AIs for `Rabbit`s and `Fox`es.

### Part 1: Fill the virtual world with life

Add at least 9 new classes to the virtual world|three Item classes for each of the three different categories listed below:
+ `Animal`s: In the animals package create three additional classes for animals, such as lions, flies, and elephants.
+ `Vehicle`s: In the vehicles package create three classes for vehicles, which can run over (destroy) everything with a lower strength but will crash (be destroyed) when running into something with a greater strength. Like real vehicles, your vehicles should build momentum when moving, so it takes time for them to accelerate or brake or turn; they can change directions only at low speed. Note that the speed of a `Vehicle` is controlled by the cool-down period.
+ Your own category: In a separate package implement three classes of `Item`s that share some similarity. Examples might include tornadoes and earthquake, mountains and cliffs, even Master Yoda or Voldemort. Use your imagination!

You have considerable freedom in this assignment for which items you add and how your items behave. Your items might range from a simple stone to sophisticated characters and weapon systems, from real-world animals to science fiction creatures, or include technical objects.

When designing your items think about subtyping and interfaces. You may want to introduce additional interfaces or abstract classes where suitable.

### Part 2: Create intelligent AIs for Rabbits and Foxes

Provide a more intelligent behavior for Rabbits and Foxes by providing an implementation of their AI classes. The best AIs are those that generate the largest average animal population over the entire time, measured separately for rabbits and foxes.

### The virtual world
This section describes the design of the virtual world and its rules.

#### Objects in the world

The world contains the following object types, with each type having different properties and specifications described here.

+ `Item`: An `Item` represents a physical object in the virtual world that occupies a field in a specific location, where it is represented with a picture. For example, `Fox`es, `Rabbit`s, and `Grass` are Items.
+ `Actor`: An Actor can actively affect the state of the world. Many `Item`s are `Actor`s; they can decide to move, eat, breed, or perform other actions. The world regularly determines each `Actor`'s next action by calling `getNextAction( )`; the Actor returns a `Command` that represents the next action. `Actor`s can act at different speeds, acting on every step in the world or only every n<sup>th</sup> step -- the speed of an actor is determined by its cool-down period.