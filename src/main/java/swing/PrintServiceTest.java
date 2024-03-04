package swing;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * В этой программе демонстрируется применение потоковых служб печати. В ней двухмерные фигуры
 * выводятся в файл формата PostScript. Если имя целевого файла не указано в командной строке,
 * полученный результат сохраняется в файле out.ps
 */
public class PrintServiceTest {
    // здесь задаются размеры изображения
    private static int IMAGE_WIDTH=300;
    private static int IMAGE_HEIGHT=300;

    public static void draw(Graphics2D g2){
        // ниже следуют инструкции для рисования:
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
            double x=(2*IMAGE_WIDTH*i)/NLINES;
            double y=(2*IMAGE_HEIGHT*(NLINES-1-i))/NLINES;
            var q=new Point2D.Double(x,y);
            g2.draw(new Line2D.Double(p,q));
        }
    }

    public static void main(String[] args) throws IOException, PrintException{
        String fileName=args.length>0?args[0]:"out.ps";
        DocFlavor flavor=DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        var mimeType="application/postscript";
        StreamPrintServiceFactory[] factories=StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor,mimeType);
        var out=new FileOutputStream(fileName);
        if (factories.length>0){
            PrintService service=factories[0].getPrintService(out);
            var doc=new SimpleDoc(new Printable() {
                @Override
                public int print(Graphics g, PageFormat pf, int page){
                  if (page>=1)
                      return Printable.NO_SUCH_PAGE;
                  else {
                      double sf1=pf.getImageableWidth()/(IMAGE_WIDTH+1);
                      double sf2=pf.getImageableHeight()/(IMAGE_HEIGHT+1);
                      double s=Math.min(sf1,sf2);
                      var g2=(Graphics2D) g;
                      g2.translate((pf.getWidth()-pf.getImageableWidth())/2,
                              (pf.getHeight()-pf.getImageableHeight())/2);
                      g2.scale(s,s);
                      draw(g2);
                      return Printable.PAGE_EXISTS;
                  }
                }
            },flavor,null);
            DocPrintJob job=service.createPrintJob();
            var attributes=new HashPrintRequestAttributeSet();
            job.print(doc,attributes);
        }
        else
            System.out.println("No factories for "+mimeType);
    }
}