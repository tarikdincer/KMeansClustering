import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class KMeansClustering extends JPanel implements ActionListener,ItemListener {
	
	private static Color[] colors=new Color[] {Color.RED,Color.BLUE,Color.CYAN,Color.BLACK,Color.GREEN,Color.ORANGE,Color.YELLOW,Color.GRAY,Color.PINK,Color.MAGENTA};
	private static int centerNumber=0;
	private static Point2D[] points;
	private static File file;
	private static int iteration=0;
	private static Clusters[] clusters;
	private static Point2D[] centers;
	static JFrame frame;
	static JComboBox<Integer> cb;
	static JTextField jt;
	static Container c;


	public KMeansClustering() {
	frame=new JFrame();
	c=frame.getContentPane();
	JButton dosyaSec=new JButton("Dosyadan sec");

	dosyaSec.addActionListener(new ActionListener() {
	
		public void actionPerformed(ActionEvent e) {
		JFileChooser jf=new JFileChooser();
		int val=jf.showOpenDialog(dosyaSec);
		if(val==JFileChooser.APPROVE_OPTION)
		file=jf.getSelectedFile();
	}
});

	c.setLayout(null);
	dosyaSec.setBounds(1100,960, 200, 20);
	c.add(dosyaSec);
	JButton kmc=new JButton("K-Means Clustering");

	kmc.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		kMeansCluster(file);
	}});

	kmc.setBounds(1320,960,240,20);
	c.add(kmc);
	cb=new JComboBox<Integer>();
	cb.addItem(1);
	cb.addItem(2);
	cb.addItem(3);
	cb.addItem(4);
	cb.addItem(5);
	cb.addItem(6);
	cb.addItem(7);
	cb.addItem(8);
	cb.addItem(9);
	cb.addItem(10);
	cb.setBounds(1040,960,50,20);
	c.add(cb);
	
	jt=new JTextField();
	jt.setBounds(900,960,100,20);
	c.add(jt);
	frame.setSize(1920,1080);
	frame.setVisible(true);






}
public static void kMeansCluster(File f) {
	
try {
	if(f.getName().indexOf("csv")<0) {
		JOptionPane.showMessageDialog(frame, "Csv dosyas� se�meniz gerekli.");
	}
	else {
	iteration=Integer.parseInt(jt.getText());
	centerNumber=cb.getSelectedIndex()+1;
	
	Scanner scan=new Scanner(f);
	int count=0;
	String okunan="";
	ArrayList<Integer> numbers=new ArrayList<>();
	
	while(scan.hasNextLine()) {
	okunan=scan.nextLine();
	int index=okunan.indexOf(";");
	numbers.add(Integer.parseInt(okunan.substring(0,index)));
	numbers.add(Integer.parseInt(okunan.substring(index+1,okunan.length())));
	count++;
	}
	
	points=new Point2D[count];
	for(int j=0;j<count;j++) {
	Point2D.Double p=new Point2D.Double();
	p.setLocation(numbers.get(2*j),numbers.get(2*j+1));	
	points[j]=p; } 
	
	
	
	randomizeCenters(centerNumber);
	findFirstClusters(points,clusters);
	for(int i=0;i<iteration;i++) {
		updateClusters(clusters);
	}
	JFrame frame2=new JFrame();
	Container c2 = frame2.getContentPane();
	c2.add(new Panel());
	frame2.pack();
	frame2.setSize(1280,720);
	frame2.setVisible(true); }

	
} catch (FileNotFoundException e) {
	e.printStackTrace();
}	
}


public static void randomizeCenters(int k) {
centers=new Point2D[k];
Point2D.Double p=new Point2D.Double();
clusters=new Clusters[k];
for(int i=0;i<k;i++) {
p.setLocation(Math.random()*1280,Math.random()*720);
centers[i]=(Point2D)p.clone();
Clusters a=new Clusters(centers[i],colors[i]);
Clusters b=new Clusters(centers[i],colors[i]);
b=a;
clusters[i]=b;
}
}
public static void findFirstClusters(Point2D[] p,Clusters[] c) {
for(int i=0;i<points.length;i++) {
double distance=1000000.0;
int a=0;
for(int j=0;j<centers.length;j++) {
if(p[i].distance(c[j].center)<distance) {
	distance=p[i].distance(c[j].center);
	a=j;
}
}
c[a].members.add(p[i]);
distance=1000000.0;
a=0;

}
for(int k=0;k<c.length;k++) {
	c[k].center.setLocation(c[k].agirlikMerkeziX(c[k]),c[k].agirlikMerkeziY(c[k]));
}
}
public static void updateClusters(Clusters[] c) {
	int b=0;
for(int i=0;i<c.length;i++) {
for(int j=0;j<c[i].members.size();j++) {
	double distance=c[i].members.get(j).distance(c[i].center);
	int a=0;
	b=0;
for(int k=0;k<c.length;k++) {
if(c[i].members.get(j).distance(c[k].center)<distance) {
	distance=c[i].members.get(j).distance(c[k].center); 
	a=k;
	b++;
}
}
if(b!=0) {
c[a].members.add(c[i].members.get(j));
c[i].members.remove(j);
}
}
}
for(int i=0;i<c.length;i++) {
	c[i].center.setLocation(c[i].agirlikMerkeziX(c[i]),c[i].agirlikMerkeziY(c[i]));

}
}

public static void main(String[] args) {
	KMeansClustering k=new KMeansClustering();
}
@Override
public void actionPerformed(ActionEvent e) {

}
@Override
public void itemStateChanged(ItemEvent ie) {
	centerNumber=cb.getSelectedIndex()+1;
	clusters=new Clusters[cb.getSelectedIndex()+1];
}
public static class Panel extends JPanel{
	@Override
	public void paint(Graphics g) {;
		for(int i=0;i<clusters.length;i++) {
			g.setColor(clusters[i].color);
			for(int j=0;j<clusters[i].members.size();j++) {
				g.fillOval((int)clusters[i].members.get(j).getX(),(int)clusters[i].members.get(j).getY(),5,5);
			}
			for(int k=0;k<clusters.length;k++) {
				g.setColor(Color.CYAN);
				g.fillOval((int)clusters[i].center.getX(), (int)clusters[i].center.getY(), 10, 10);
			}
		}
	}
}
}

