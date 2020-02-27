package com.github.gumtreediff.tree;

import java.util.ArrayList;

public class TimeTree extends Tree{

    long time;

     String label;

    // Begin position of the tree in terms of absolute character index and length
     int pos;
     int length;
    // End position

     AssociationMap metadata;

	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public TimeTree(int type, String label,long time) {
		super(type, label);
		this.time = time;
	}
    // Only used for cloning ...
    protected TimeTree(Tree other,long time) {
        super(other);
        this.time = time;
    }
    
    // Only used for cloning ...
    public TimeTree(TimeTree other) {
        super(other);
        this.time = other.time;
    }
    
    @Override
    public TimeTree deepCopy() {
        TimeTree copy = new TimeTree(this);
        for (ITree child : getChildren())
            copy.addChild(child.deepCopy());
        return copy;
    }
    public static TimeTree deepCopy(Tree tree,long time) {
        TimeTree copy = new TimeTree(tree,time);
        for (ITree child : tree.getChildren())
            copy.addChild(deepCopy((Tree)child,time));
        return copy;
    }
    public String toShortString() {
    	return super.toShortString() + ", " + getId() + ", " + getTime()%10000+"  ";
    }
}
