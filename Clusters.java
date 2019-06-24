import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
public class Clusters {
protected Point2D center;
protected Color color;
protected ArrayList<Point2D> members=new ArrayList<Point2D>();

public Clusters(Point2D cent,Color c) {
	center = cent;
	color = c;
}
public double agirlikMerkeziX(Clusters c) {
	double toplam = 0;
	
	for(int i = 0; i < c.members.size(); i++) {
		toplam += c.members.get(i).getX();
										}
	double a = c.members.size();
	
	return (double)(toplam/a);
}

public double agirlikMerkeziY(Clusters c) {
	double toplam = 0;
	
	for(int i = 0; i < c.members.size(); i++) {
	toplam += c.members.get(i).getY();
}
	double b = c.members.size();
	
	return (toplam/b);
}
}
