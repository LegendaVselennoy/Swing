package swing;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new PrintTestFrame();
            frame.setTitle("PrintTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * В этом фрейме отображается панель с двухмерной графикой и экранными кнопками
 * для печати графики и установки формата страницы
 */
class PrintTestFrame extends JFrame{
    private PrintComponent canvas;
    private PrintRequestAttributeSet attributes;

    public PrintTestFrame(){
        canvas=new PrintComponent();
        add(canvas,BorderLayout.CENTER);

        attributes=new HashPrintRequestAttributeSet();

        var buttonPanel=new JPanel();
        var printButton=new JButton("Print");
        buttonPanel.add(printButton);
        printButton.addActionListener(event->{
            try{
                PrinterJob job=PrinterJob.getPrinterJob();
                job.setPrintable(canvas);
                if (job.printDialog(attributes))
                    job.print(attributes);
            }catch (PrinterException ex){
                JOptionPane.showMessageDialog(PrintTestFrame.this,ex);
            }
        });
        var pageSetupButton=new JButton("Page setup");
        buttonPanel.add(pageSetupButton);
        pageSetupButton.addActionListener(event->{
            PrinterJob job=PrinterJob.getPrinterJob();
            job.pageDialog(attributes);
        });
        add(buttonPanel,BorderLayout.NORTH);
        pack();
    }
}

/**
 * Этот компонент формирует двухмерную графику для вывода на экран и на печать
 */
class PrintComponent extends JComponent implements Printable{
    private static final Dimension PREFERRED_SIZE=new Dimension(300,300);

    public void paintComponent(Graphics g){
        var g2=(Graphics2D) g;
        drawPage(g2);
    }

    public int print(Graphics g, PageFormat pf,int page) throws PrinterException{
        if (page>=1) return Printable.NO_SUCH_PAGE;
        var g2=(Graphics2D) g;
        g2.translate(pf.getImageableX(),pf.getImageableY());
        g2.draw(new Rectangle2D.Double(0,0,pf.getImageableWidth(),pf.getImageableHeight()));

        drawPage(g2);
        return Printable.PAGE_EXISTS;
    }

    /**
     * Этот метод рисует страницу в графическом контексте экрана и печатающего устройства
     * @param g2 Графический контекст
     */
    public void drawPage(Graphics2D g2){
        FontRenderContext context=g2.getFontRenderContext();
        var f=new Font("Serif",Font.PLAIN,72);
        var clipShape=new GeneralPath();

        var layout=new TextLayout("Hello",f,context);
        AffineTransform transform=AffineTransform.getTranslateInstance(0,72);
        Shape outline=layout.getOutline(transform);
        clipShape.append(outline,false);

        layout=new TextLayout("World",f,context);
        transform=AffineTransform.getTranslateInstance(0,144);
        outline=layout.getOutline(transform);
        clipShape.append(outline,false);

        g2.draw(clipShape);
        g2.clip(clipShape);

        final int NLINES=50;
        var p=new Point2D.Double(0,0);
        for (int i = 0; i < NLINES; i++) {
            double x=(2*getWidth()*i)/NLINES;
            double y=(2*getHeight()*(NLINES-1-i))/NLINES;
            var q=new Point2D.Double(x,y);
            g2.draw(new Line2D.Double(p,q));
        }
    }

    public Dimension getPreferredSize(){
        return PREFERRED_SIZE;
    }
}
