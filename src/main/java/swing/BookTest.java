package swing;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.*;

public class BookTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new BookTestFrame();
            frame.setTitle("BookTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит текстовое поле для ввода баннера, а также кнопки для печати,
 * настройки и предварительного просмотра печатаемой страницы
 */
class BookTestFrame extends JFrame{
    private JTextField text;
    private PageFormat pageFormat;
    private PrintRequestAttributeSet attributes;

    public BookTestFrame(){
        text=new JTextField();
        add(text, BorderLayout.NORTH);
        attributes=new HashPrintRequestAttributeSet();

        var buttonPanel=new JPanel();

        var printButton=new JButton("Print");
        buttonPanel.add(printButton);
        printButton.addActionListener(event->{
            try{
                PrinterJob job=PrinterJob.getPrinterJob();
                job.setPageable(makeBook());
                if (job.printDialog(attributes))
                    job.print(attributes);
            }catch (PrinterException ex){
                JOptionPane.showMessageDialog(BookTestFrame.this,ex);
            }
        });
        var pageSetupButton=new JButton("Page setup");
        buttonPanel.add(pageSetupButton);
        pageSetupButton.addActionListener(event->{
            PrinterJob job=PrinterJob.getPrinterJob();
            pageFormat=job.pageDialog(attributes);
        });
        var printPreviewButton=new JButton("Print preview");
        buttonPanel.add(printPreviewButton);
        printPreviewButton.addActionListener(event->{
            var dialog=new PrintPreviewDialog(makeBook());
            dialog.setVisible(true);
        });
        add(buttonPanel,BorderLayout.SOUTH);
        pack();
    }

    /**
     * Составляет книгу из страницы обложки и страниц баннера
     */
    public Book makeBook(){
        if (pageFormat==null){
            PrinterJob job=PrinterJob.getPrinterJob();
            pageFormat=job.defaultPage();
        }
        var book=new Book();
        String message=text.getText();
        var banner=new Banner(message);
        int pageCount=banner.getPageCount((Graphics2D)getGraphics(),pageFormat);
        book.append(new CoverPage(message+" ("+pageCount+" pages)"),pageFormat);
        book.append(banner,pageFormat,pageCount);
        return book;
    }
}

/**
 * Баннер для печати текстовой строки на нескольких страницах
 */
class Banner implements Printable{
    private String  message;
    private double scale;

    /**
     * Конструирует баннер
     * @param m Строка сообщения
     */
    public Banner(String m){
        message=m;
    }

    /**
     * Получает количество страниц в данном разделе
     * @param g2 Графический контекст
     * @param pf Формат страницы
     * @return Количество требующихся страниц
     */
    public int getPageCount(Graphics2D g2,PageFormat pf){
        if (message.equals("")) return 0;
        FontRenderContext context=g2.getFontRenderContext();
        var f=new Font("Serif",Font.PLAIN,72);
        Rectangle2D bounds=f.getStringBounds(message,context);
        scale=pf.getImageableHeight()/bounds.getHeight();
        double width=scale*bounds.getWidth();
        int pages=(int) Math.ceil(width/pf.getImageableWidth());
        return pages;
    }

    public int print(Graphics g,PageFormat pf,int page) throws PrinterException{
        var g2=(Graphics2D) g;
        if (page>getPageCount(g2,pf))
            return Printable.NO_SUCH_PAGE;
        g2.translate(pf.getImageableX(),pf.getImageableY());

        drawPage(g2,pf,page);
        return Printable.PAGE_EXISTS;
    }

    public void drawPage(Graphics2D g2,PageFormat pf,int page){
        if (message.equals("")) return;
        page--; // Учитывать страницу обложки

        drawCropMarks(g2,pf);
        g2.clip(new Rectangle2D.Double(0,0,pf.getImageableWidth(),pf.getImageableHeight()));
        g2.translate(-page*pf.getImageableWidth(),0);
        g2.scale(scale,scale);
        FontRenderContext context=g2.getFontRenderContext();
        var f=new Font("Serif",Font.PLAIN,72);
        var layout=new TextLayout(message,f,context);
        AffineTransform transform=AffineTransform.getTranslateInstance(0,layout.getAscent());
        Shape outline=layout.getOutline(transform);
        g2.draw(outline);
    }

    /**
     * Рисует полудюймовые метки обрезки в углах страницы
     * @param g2 Графический контекст
     * @param pf Формат страницы
     */
    public void drawCropMarks(Graphics2D g2,PageFormat pf){
        // длина метки обрезки=1/2 дюйма;
        final double C=36;
        double w=pf.getImageableWidth();
        double h=pf.getImageableHeight();
        g2.draw(new Line2D.Double(0,0,0,C));
        g2.draw(new Line2D.Double(0,0,C,0));
        g2.draw(new Line2D.Double(w,0,w,C));
        g2.draw(new Line2D.Double(w,0,w-C,0));
        g2.draw(new Line2D.Double(0,h,0,h-C));
        g2.draw(new Line2D.Double(0,h,C,h));
        g2.draw(new Line2D.Double(w,h,w,h-C));
        g2.draw(new Line2D.Double(w,h,w-C,h));
    }
}

/**
 * Этот класс печатает страницу обложки с заглавием
 */
class CoverPage implements Printable{
    private String title;

    /**
     * Конструирует страницу обложки
     * @param t Заглавие
     */
    public CoverPage(String t){
        title=t;
    }

    public int print(Graphics g,PageFormat pf,int page) throws PrinterException{
        if (page>=1) return Printable.NO_SUCH_PAGE;
        var g2=(Graphics2D) g;
        g2.setPaint(Color.black);
        g2.translate(pf.getImageableX(),pf.getImageableY());
        FontRenderContext context=g2.getFontRenderContext();
        Font f=g2.getFont();
        var layout=new TextLayout(title,f,context);
        float ascent=layout.getAscent();
        g2.drawString(title,0,ascent);
        return Printable.PAGE_EXISTS;
    }
}

/**
 * Этот класс реализует типичное окно предварительного просмотра печати
 */
class PrintPreviewDialog extends JDialog{
    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=300;
    private PrintPreviewCanvas canvas;

    /**
     * Конструирует диалоговое окно предварительного просмотра печати
     * @param p Печатаемый объект типа Printable
     * @param pf Формат страницы
     * @param pages Количество страниц в печатаемом объекте p
     */
    public PrintPreviewDialog(Printable p,PageFormat pf,int pages){
        var book=new Book();
        book.append(p,pf,pages);
        layoutUI(book);
    }

    /**
     * Создаёт диалоговое окно предварительного просмотра печати
     * @param b Объект книги типа Book
     */
    public PrintPreviewDialog(Book b){
        layoutUI(b);
    }

    /**
     * Компонует ГПИ в диалоговом окне
     * @param book Предварительно просматривая книга
     */
    public void layoutUI(Book book){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        canvas=new PrintPreviewCanvas(book);
        add(canvas,BorderLayout.CENTER);

        var buttonPanel=new JPanel();

        var nextButton=new JButton("Next");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(event->
                canvas.flipPage(1));

        var previousButton=new JButton("Previous");
        buttonPanel.add(previousButton);
        previousButton.addActionListener(event->
                canvas.flipPage(-1));

        var closeButton=new JButton("Close");
        buttonPanel.add(closeButton);
        closeButton.addActionListener(event->setVisible(false));
        add(buttonPanel,BorderLayout.SOUTH);
    }
}

/**
 * Холст для отображения предварительного просмотра печати
 */
class PrintPreviewCanvas extends JComponent{
    private  Book book;
    private int currentPage;

    /**
     * Конструирует холст для предварительного просмотра печати
     * @param b Предварительно просматривая книга
     */
    public PrintPreviewCanvas(Book b){
        book=b;
        currentPage=0;
    }

    public void paintComponent(Graphics g){
        var g2=(Graphics2D) g;
        PageFormat pageFormat=book.getPageFormat(currentPage);
        // координата x смещения начала страницы в окне:
        double xoff;
        // координата y смещения начала страницы в окне:
        double yoof;
        // масштабный коэффициент для подгонки просматриваемой страницы по размерам окна:
        double scale;
        double px=pageFormat.getWidth();
        double py=pageFormat.getHeight();
        double sx=getWidth()-1;
        double sy=getHeight()-1;
        // отцентровать по горизонтали:
        if (px/py<sx/sy){
            scale=sy/py;
            xoff=0.5*(sx-scale*px);
            yoof=0;
        }else {
            // отцентровать по вертикали:
            scale = sx / px;
            xoff = 0;
            yoof = 0.5 * (sy - scale * py);
        }
        g2.translate((float)xoff,(float)yoof);
        g2.scale((float)scale,(float)scale);

        // нарисовать контуры страницы page, игнорируя поля:
        var page=new Rectangle2D.Double(0,0,px,py);
        g2.setPaint(Color.white);
        g2.fill(page);
        g2.setPaint(Color.black);
        g2.draw(page);

        Printable printable=book.getPrintable(currentPage);

        try{
            printable.print(g2,pageFormat,currentPage);
        }catch (PrinterException e){
            g2.draw(new Line2D.Double(0,0,px,py));
            g2.draw(new Line2D.Double(px,0,0,py));
        }
    }

    /**
     * Листать книгу на заданное число страниц
     * @param by Число листаемых страниц.
     * Отрицательные значения данного параметра обозначают листание книги назад
     */
    public  void flipPage(int by){
        int newPage=currentPage+by;
        if (0<=newPage && newPage<book.getNumberOfPages()){
            currentPage=newPage;
            repaint();
        }
    }
}