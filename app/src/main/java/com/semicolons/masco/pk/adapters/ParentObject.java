package com.semicolons.masco.pk.adapters;

public class ParentObject {

    public String mother;


    //Header and footer don’t need to be a String,
    //they can be a class or whatever depend on your need.
    public String textToHeader;


    //Parent should have a list of their child
    //public List<ChildObject> childObjects;

//    public ParentObject(String mother, String textToHeader, List<ChildObject> childObjects)
//    {
//        this.mother = mother;
//        this.textToHeader = textToHeader;
//        this.childObjects = childObjects;
//
//    }

    public ParentObject(String mother) {
        this.mother = mother;
//        this.textToHeader = textToHeader;
//        this.childObjects = childObjects;

    }
}
