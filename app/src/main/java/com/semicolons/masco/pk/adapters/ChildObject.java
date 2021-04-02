package com.semicolons.masco.pk.adapters;

import java.util.List;

public class ChildObject {
    public String childName;
    public String textToHeader;

    public List<ParentObject> parentObjects;

//    public ChildObject(String childName)
//    {
//        this.childName = childName;
//
//    }
//


    public ChildObject(String childName, String textToHeader, List<ParentObject> parentObjects) {
        this.childName = childName;
        this.textToHeader = textToHeader;
        this.parentObjects = parentObjects;
    }


}